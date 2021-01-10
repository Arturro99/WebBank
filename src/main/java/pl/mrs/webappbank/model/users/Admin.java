package pl.mrs.webappbank.model.users;

import lombok.Data;

import java.util.UUID;

@Data
public class Admin {
    private UUID pid;
    private String login;
    private String password;


    public Admin() {
    }

    public Admin(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
