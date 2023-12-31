package hexlet.code.dto.urls;

import hexlet.code.model.Url;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UrlsPage extends BasePage {
    private List<Url> urls;
    private int pageNumber;
}
