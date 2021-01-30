package pl.mrs.webappbank.model.users;

import lombok.Data;

import java.util.UUID;

@Data
public class Employee extends Person {
    private boolean editable;

    public Employee() {
        super();
    }

    public Employee(String name, String surname, String login, String password) {
        super(name, surname, login, password);
    }

    @Override
    public UUID getPid() {
        return this.pid;
    }

    @Override
    public String[] getAccessLevel() {
        return new String[]{"Clients", "Employees"};
    }
}
