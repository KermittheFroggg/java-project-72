package hexlet.code.model;


import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UrlCheck {
    private long id;
    private long statusCode;
    private String title;
    private String h1;
    private StringBuilder description;
    private long urlId;
    private Timestamp createdAt;

    public UrlCheck(Long statusCode, String title, String h1, StringBuilder description, Long urlId, Timestamp createdAt) {
        this.statusCode = statusCode;
        this.title = title;
        this.h1 = h1;
        this.description = description;
        this.urlId = urlId;
        this.createdAt = createdAt;
    }
}
