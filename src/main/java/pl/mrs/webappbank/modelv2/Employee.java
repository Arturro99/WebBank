package pl.mrs.webappbank.modelv2;

import lombok.Data;

import java.util.UUID;

@Data
public class Employee {
    private UUID pid;
    private String name;
    private String surname;
    private String login;
    private String password;
    private boolean editable;

    public Employee() {
        this.pid = UUID.randomUUID();
    }

    public Employee(String login, String password, String name, String surname) {
        this.pid = UUID.randomUUID();
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }
}
