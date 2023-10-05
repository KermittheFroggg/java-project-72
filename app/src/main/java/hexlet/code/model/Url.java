package hexlet.code.model;

import java.util.Date;

public class Url {
    private long id;
    private String name;
    private Date createdAt;

    public Url (String name) {
        this.name = name;
        this.createdAt = new Date();
    }
}
