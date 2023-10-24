package gg.jte.generated.ondemand.urls;
import hexlet.code.utils.NamedRoutes;
import hexlet.code.dto.urls.UrlsPage;
import java.text.SimpleDateFormat;
public final class JteurlsGenerated {
	public static final String JTE_NAME = "urls/urls.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,3,3,3,5,5,7,7,22,22,24,24,24,26,26,26,26,26,26,26,26,26,26,26,28,28,30,30,30,32,32,32,33,33,36,36,38,38,45,45,45,45,48,48,48,48,48,48,48,51,51,51,51,58,58,58};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlsPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layouts.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <main class=\"flex-grow-1\">\n        <section>\n            <div class=\"container-lg mt-5\">\n                <h1>Сайты</h1>\n                <table class=\"table table-bordered table-hover mt-3\">\n                    <thead>\n                    <tr>\n                        <th class=\"col-1\">ID</th>\n                        <th>Имя</th>\n                        <th class=\"col-2\">Последняя проверка</th>\n                        <th class=\"col-1\">Код ответа</th>\n                    </tr>\n                    </thead>\n                    <tbody>\n                    ");
				for (var url : page.getUrls()) {
					jteOutput.writeContent("\n                        <tr>\n                            <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(url.getId());
					jteOutput.writeContent("</td>\n                            <td>\n                                <a href=\"");
					jteOutput.setContext("a", "href");
					jteOutput.writeUserContent(NamedRoutes.urls());
					jteOutput.setContext("a", null);
					jteOutput.writeContent("/");
					jteOutput.setContext("a", "href");
					jteOutput.writeUserContent(url.getId());
					jteOutput.setContext("a", null);
					jteOutput.writeContent("\">");
					jteOutput.setContext("a", null);
					jteOutput.writeUserContent(url.getName());
					jteOutput.writeContent("</a>\n                            </td>\n                            ");
					if (url.getLastCheck() != null) {
						jteOutput.writeContent("\n                                <td class=\"col-2\">\n                                    ");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(new SimpleDateFormat("MM/dd/yyyy hh:mm").format(url.getLastCheck().getCreatedAt()));
						jteOutput.writeContent("\n                                </td>\n                                <td class=\"col-2\">");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(url.getLastCheck().getStatusCode());
						jteOutput.writeContent("</td>\n                            ");
					} else {
						jteOutput.writeContent("\n                                <td class=\"col-2\"></td>\n                                <td class=\"col-2\"></td>\n                            ");
					}
					jteOutput.writeContent("\n                        </tr>\n                    ");
				}
				jteOutput.writeContent("\n\n                    </tbody>\n                </table>\n                <nav aria-label=\"Page navigation\">\n                    <ul class=\"pagination justify-content-center mt-5\">\n                        <li class=\"page-item\">\n                            <a class=\"page-link\" href=\"/urls?page=");
				jteOutput.setContext("a", "href");
				jteOutput.writeUserContent(page.getPageNumber() - 1);
				jteOutput.setContext("a", null);
				jteOutput.writeContent("\">Previous</a>\n                        </li>\n                        <li class=\"page-item\">\n                            <a class=\"page-link\" href=\"/urls?page=");
				jteOutput.setContext("a", "href");
				jteOutput.writeUserContent(page.getPageNumber());
				jteOutput.setContext("a", null);
				jteOutput.writeContent("\">");
				jteOutput.setContext("a", null);
				jteOutput.writeUserContent(page.getPageNumber());
				jteOutput.writeContent("</a>\n                        </li>\n                        <li class=\"page-item\">\n                            <a class=\"page-link\" href=\"/urls?page=");
				jteOutput.setContext("a", "href");
				jteOutput.writeUserContent(page.getPageNumber() + 1);
				jteOutput.setContext("a", null);
				jteOutput.writeContent("\">Next</a>\n                        </li>\n                    </ul>\n                </nav>\n            </div>\n        </section>\n    </main>\n");
			}
		}, null);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlsPage page = (UrlsPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
