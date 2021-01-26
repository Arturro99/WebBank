package pl.mrs.webappbank.model.users;

import lombok.Data;
import pl.mrs.webappbank.model.SignableEntity;
import pl.mrs.webappbank.model.accounts.Account;
import pl.mrs.webappbank.restapi.adapters.SerializeStringToEmptyAdapter;

import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.annotation.JsonbTypeAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// todo move  pid initialization to the repository
@Data
public abstract class Person implements SignableEntity {

    protected UUID pid;
    protected String name;
    protected String surname;
    protected String login;
    protected String password;
    protected int age;

    public Person() {
    }

    public Person(String name, String surname, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
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

    @JsonbTypeAdapter(SerializeStringToEmptyAdapter.class)
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
}
