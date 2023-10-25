@import hexlet.code.utils.NamedRoutes
@import hexlet.code.dto.urls.UrlsPage
@import java.text.SimpleDateFormat
@param  UrlsPage page

@template.layouts.page(
page = page,
content = @`
    <main class="flex-grow-1">
        <section>
            <div class="container-lg mt-5">
                <h1>Сайты</h1>
                <table class="table table-bordered table-hover mt-3">
                    <thead>
                    <tr>
                        <th class="col-1">ID</th>
                        <th>Имя</th>
                        <th class="col-2">Последняя проверка</th>
                        <th class="col-1">Код ответа</th>
                    </tr>
                    </thead>
                    <tbody>
                    @for(var url : page.getUrls())
                        <tr>
                            <td>${url.getId()}</td>
                            <td>
                                <a href="${NamedRoutes.urls()}/${url.getId()}">${url.getName()}</a>
                            </td>
                            @if (url.getLastCheck() != null)
                                <td class="col-2">
                                    ${new SimpleDateFormat("MM/dd/yyyy hh:mm").format(url.getLastCheck().getCreatedAt())}
                                </td>
                                <td class="col-2">${url.getLastCheck().getStatusCode()}</td>
                            @else
                                <td class="col-2"></td>
                                <td class="col-2"></td>
                            @endif
                        </tr>
                    @endfor

                    </tbody>
                </table>
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center mt-5">
                        <li class="page-item">
                            <a class="page-link" href="/urls?page=${page.getPageNumber() - 1}">Previous</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="/urls?page=${page.getPageNumber()}">${page.getPageNumber()}</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="/urls?page=${page.getPageNumber() + 1}">Next</a>
                        </li>
                    </ul>
                </nav>
            </div>
        </section>
    </main>
`
)