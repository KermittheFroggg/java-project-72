package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.urls.UrlCheckPage;
import hexlet.code.utils.NamedRoutes;;
import java.text.SimpleDateFormat;
public final class JteurlGenerated {
	public static final String JTE_NAME = "urls/url.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,3,3,3,5,5,7,7,11,11,11,16,16,16,20,20,20,24,24,24,29,29,29,29,29,29,29,29,42,42,43,43,45,45,45,46,46,46,47,47,47,48,48,48,49,49,49,50,50,50,52,52,53,53,59,59,59};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlCheckPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layouts.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <main class=\"flex-grow-1\">\n        <section>\n            <div class=\"container-lg mt-5\">\n                <h1>Сайт ");
				jteOutput.setContext("h1", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</h1>\n                <table class=\"table table-bordered table-hover mt-3\">\n                    <tbody>\n                    <tr>\n                        <td>ID</td>\n                        <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getId());
				jteOutput.writeContent("</td>\n                    </tr>\n                    <tr>\n                        <td>Имя</td>\n                        <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</td>\n                    </tr>\n                    <tr>\n                        <td>Дата создания</td>\n                        <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(new SimpleDateFormat("MM/dd/yyyy hh:mm").format(page.getUrl().getCreatedAt()));
				jteOutput.writeContent("</td>\n                    </tr>\n                    </tbody>\n                </table>\n                <h2 class=\"mt-5\">Проверки</h2>\n                <form method=\"post\"");
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(NamedRoutes.checkUrl(page.getUrl().getId()))) {
					jteOutput.writeContent(" action=\"");
					jteOutput.setContext("form", "action");
					jteOutput.writeUserContent(NamedRoutes.checkUrl(page.getUrl().getId()));
					jteOutput.setContext("form", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(">\n                    <button type=\"submit\" class=\"btn btn-primary\">Запустить проверку</button>\n                </form>\n                <table class=\"table table-bordered table-hover mt-3\">\n                    <thead>\n                    <th class=\"col-1\">ID</th>\n                    <th class=\"col-1\">Код ответа</th>\n                    <th>title</th>\n                    <th>h1</th>\n                    <th>description</th>\n                    <th class=\"col-2\">Дата проверки</th>\n                    </thead>\n                    <tbody>\n                    ");
				if (page.getUrlChecks() != null) {
					jteOutput.writeContent("\n                        ");
					for (var urlCheck : page.getUrlChecks()) {
						jteOutput.writeContent("\n                            <tr>\n                                <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(urlCheck.getId());
						jteOutput.writeContent("</td>\n                                <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(urlCheck.getStatusCode());
						jteOutput.writeContent("</td>\n                                <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(urlCheck.getTitle());
						jteOutput.writeContent("</td>\n                                <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(urlCheck.getH1());
						jteOutput.writeContent("</td>\n                                <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(String.valueOf(urlCheck.getDescription()));
						jteOutput.writeContent("</td>\n                                <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(new SimpleDateFormat("MM/dd/yyyy hh:mm").format(urlCheck.getCreatedAt()));
						jteOutput.writeContent("</td>\n                            </tr>\n                        ");
					}
					jteOutput.writeContent("\n                    ");
				}
				jteOutput.writeContent("\n                    </tbody>\n                </table>\n            </div>\n        </section>\n    </main>\n");
			}
		}, null);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlCheckPage page = (UrlCheckPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
