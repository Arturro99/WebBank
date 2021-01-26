package pl.mrs.webappbank.model.users;

import lombok.Data;

import java.util.UUID;

@Data
public class Admin extends Person {

    public Admin() {
    }

    @Override
    public UUID getPid() {
        return pid;
    }

    public Admin(String login, String password) {
        this.age = 99;
        this.name = "ADMIN";
        this.surname = "ADMIN";
        this.login = login;
        this.password = password;
    }
}
