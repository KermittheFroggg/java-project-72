package hexlet.code.dto.urls;

import hexlet.code.model.Url;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class UrlPage extends BasePage {
    private Url url;
}
