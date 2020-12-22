package pl.mrs.webappbank.modelv2;

import lombok.Data;
import pl.mrs.webappbank.modelv2.accounts.Account;
import pl.mrs.webappbank.repositories.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
