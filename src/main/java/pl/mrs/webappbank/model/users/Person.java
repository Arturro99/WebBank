package pl.mrs.webappbank.model.users;

import lombok.Data;
import pl.mrs.webappbank.model.SignableEntity;
import pl.mrs.webappbank.model.accounts.Account;
import pl.mrs.webappbank.restapi.adapters.SerializeStringToEmptyAdapter;

import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
public abstract class Person implements SignableEntity {

    protected UUID pid;
    @NotEmpty
    protected String name;
    @NotEmpty
    protected String surname;
    @NotEmpty
    protected String login;
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,32}", message = "Not satisfied password")
    protected String password;
    @Positive
    @Max(value = 100)
    protected int age;
    private boolean blocked;

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public Person() {
    }

    public Person(String name, String surname, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }

    public Person(String name, String surname, String login, String password, boolean blocked) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.blocked = blocked;
    }

    @Override
    public String toString() {
        return name +
                " " + surname +
                ", login='" + login + '\'';
    }

    public abstract UUID getPid();

    public void setId(UUID id){
        this.pid = id;
    }

    //@JsonbTypeAdapter(SerializeStringToEmptyAdapter.class)
    public String getPassword(){
        return this.password;
    }

    public String getLogin() {
        return this.login;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    @Override
    @JsonbTransient
    public String getSignablePayload() {
        if(null == pid)
            return "";
        return pid.toString();
    }

    public abstract String[] getAccessLevel();
}
