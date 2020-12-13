package pl.mrs.webappbank.modelv2;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mrs.webappbank.modelv2.accounts.Account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Client implements Serializable {
    private UUID pid;
    private String name;
    private String surname;
    private String login;
    private String password;
    private int age;
    private boolean editable;
    List<Account> listOfAccounts = new ArrayList<>();

    public Client() {
        this.pid = UUID.randomUUID();
    }

    public Client(String login, String password, String name, String surname, int age) {
        this.pid = UUID.randomUUID();
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.listOfAccounts = new ArrayList<>();
    }


//    @Override
//    public Object clone() {
//        Client clone = new Client(login, password, name,surname,age);
//        clone.listOfAccounts = new ArrayList<>(listOfAccounts.size());
//        clone.listOfAccounts.addAll(listOfAccounts);
//        return clone;
//    }

    public void addAccount(Account account) {
        listOfAccounts.add(account);
    }
    public void deleteAccount(Account account){listOfAccounts.remove(account);}

    @Override
    public String toString() {
        return name +
                " " + surname +
                ", login='" + login + '\'' +
                ", age=" + age;
    }
}
