package pl.mrs.webappbank.modelv2;

import lombok.Data;

import java.util.UUID;

@Data
public class Admin {
    private UUID pid;
    private String login;
    private String password;

    public Admin() {
        this.pid = UUID.randomUUID();
    }

    public Admin(String login, String password) {
        this.pid = UUID.randomUUID();
        this.login = login;
        this.password = password;
    }
}
