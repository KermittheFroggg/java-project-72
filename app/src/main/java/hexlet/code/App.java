package hexlet.code;

import hexlet.code.controller.UrlController;
import hexlet.code.dto.urls.BasePage;
import hexlet.code.repository.BaseRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Collections;
import java.util.stream.Collectors;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import hexlet.code.utils.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import lombok.extern.slf4j.Slf4j;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;

@Slf4j
public class App {
    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.valueOf(port);
    }

    public static void main(String[] args) throws IOException, SQLException {
        var app = getApp();
        app.start(getPort());
    }

    public static HikariDataSource getDataSource() {
        String environment = System.getenv("APP_ENV");
        HikariConfig config = new HikariConfig();
        if ("prod".equals(environment)) {
            String dbUrl = "jdbc:postgresql://"
                    + System.getenv("DB_HOST")
                    + ":"
                    + System.getenv("DB_PORT")
                    + "/"
                    + System.getenv("DB_NAME");
            config.setJdbcUrl(dbUrl);
            config.setUsername(System.getenv("DB_USER"));
            config.setPassword(System.getenv("DB_PASSWORD"));
        } else {
            config.setJdbcUrl("jdbc:h2:mem:devdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        }
        return new HikariDataSource(config);
    }

    public static String getResourceFileAsString(String fileName) throws IOException {
        var schema = App.class.getClassLoader().getResource(fileName);
        var file = new File(schema.getFile());
        var sql = Files.lines(file.toPath())
                .collect(Collectors.joining("\n"));
        return sql;
    }

    public static Javalin getApp() throws IOException, SQLException {
        JavalinJte.init(createTemplateEngine());

        var dataSource = getDataSource();
        var sql = getResourceFileAsString("schema.sql");
        log.info(sql);
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }
        BaseRepository.dataSource = dataSource;

        var app = Javalin.create(config -> {
            config.plugins.enableDevLogging();
        });

        app.get(NamedRoutes.root(), ctx -> {
            var page = new BasePage();
            page.setFlash(ctx.consumeSessionAttribute("flash"));
            ctx.render("index.jte", Collections.singletonMap("page", page));
        });
        app.post(NamedRoutes.urls(), UrlController::create);
        app.get(NamedRoutes.urls(), UrlController::index);
        app.get(NamedRoutes.showUrl("{id}"), UrlController::showUrl);
        app.post(NamedRoutes.checkUrl("{id}"), UrlController::checkUrl);

        return app;
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
        return templateEngine;
    }
}
