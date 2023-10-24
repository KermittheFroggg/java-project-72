package hexlet.code.model;

import java.sql.Timestamp;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Url {
    private long id;
    private String name;
    private Timestamp createdAt;
    private UrlCheck lastCheck;

    public Url (String name, Timestamp createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }
}
