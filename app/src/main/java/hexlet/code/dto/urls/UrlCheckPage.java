package hexlet.code.dto.urls;
import java.util.List;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class UrlCheckPage extends BasePage {
    private List<UrlCheck> urlChecks;
    private Url url;
}
