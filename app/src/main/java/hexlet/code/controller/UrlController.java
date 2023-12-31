package hexlet.code.controller;

import hexlet.code.dto.urls.UrlCheckPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.NamedRoutes;
import io.javalin.http.Context;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Comparator;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;

public class UrlController {
    public static void index(Context ctx) throws SQLException {
        List<Url> urls = UrlRepository.getEntities();
        List<UrlCheck> urlChecks = UrlCheckRepository.getEntities();
        Integer pageNumber = 1;
        if (urls.size() > 10) {
            pageNumber = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
            int per = 5;
            int offset = (pageNumber - 1) * per;
            if (offset + per < urls.size()) {
                urls = urls.subList(offset, offset + per);
            } else {
                urls = urls.subList(offset, urls.size());
            }
        }
        for (Url url : urls) {
            Optional<UrlCheck> lastUrlCheck = urlChecks.stream()
                    .filter(u -> u.getUrlId() == url.getId())
                    .max(Comparator.comparing(UrlCheck::getCreatedAt));
            lastUrlCheck.ifPresent(url::setLastCheck);
        }
        UrlsPage page = new UrlsPage(urls, pageNumber);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("urls/urls.jte", Collections.singletonMap("page", page));
    }

    public static void create(Context ctx) throws SQLException, RuntimeException {
        String name = ctx.formParamAsClass("url", String.class).get();
        try {
            URL urlBeforeChecking = new URL(name);
            String urlAfterCheking = urlBeforeChecking.getProtocol() + "://" + urlBeforeChecking.getAuthority();
            if (UrlRepository.findByName(urlAfterCheking).isPresent()) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.sessionAttribute("flash-type", "error");
                ctx.redirect(NamedRoutes.urls());
                return;
            }
            Date date = new Date();
            Timestamp createdAt = new Timestamp(date.getTime());
            Url url = new Url(urlAfterCheking, createdAt);
            UrlRepository.save(url);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.sessionAttribute("flash-type", "success");
            ctx.redirect(NamedRoutes.urls());
        } catch (MalformedURLException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flash-type", "error");
            ctx.redirect(NamedRoutes.root());
        }
    }

    public static void showUrl(Context ctx) throws SQLException {
        Long id = ctx.pathParamAsClass("id", Long.class).get();

        Url url = UrlRepository.find(id).get();

        List<UrlCheck> urlChecks = new ArrayList<>(UrlCheckRepository.getEntities()).stream()
                .filter(u -> u.getUrlId() == id)
                .toList();
        UrlCheckPage page = new UrlCheckPage(urlChecks, url);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("urls/url.jte", Collections.singletonMap("page", page));
    }

    public static void checkUrl(Context ctx) throws SQLException {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository.find(id).get();
        try {
            HttpResponse<String> response = Unirest.get(url.getName())
                    .asString();

            Document doc = Jsoup.parse(response.getBody());
            Long statusCode = (long) response.getStatus();
            String h1 = doc.select("h1").first().text();
            String title = doc.select("title").first().text();
            StringBuilder description = new StringBuilder(doc.select("meta[name=description][content]")
                    .attr("content"));
            Long urlId = id;
            Date date = new Date();
            Timestamp createdAt = new Timestamp(date.getTime());
            UrlCheck urlCheck = new UrlCheck(statusCode, title, h1, description, urlId, createdAt);
            UrlCheckRepository.save(urlCheck);
            Unirest.shutDown();
            ctx.redirect(NamedRoutes.showUrl(id));
        } catch (UnirestException e) {
            ctx.sessionAttribute("flash", "Некорректный адрес");
            ctx.sessionAttribute("flash-type", "error");
            ctx.redirect(NamedRoutes.showUrl(id));
        }
    }
}
