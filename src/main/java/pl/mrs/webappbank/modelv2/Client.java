package pl.mrs.webappbank.modelv2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mrs.webappbank.model.accounts.Account;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@NoArgsConstructor
public class Client implements Serializable {
    private String pid;
    private String name;
    private String surname;
    private String login;
    private String password;
    private int age;
    ArrayList<Account> listOfAccounts;

    public Client(String pid, String login, String password, String name, String surname, int age) {
        this.pid = pid;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.listOfAccounts = new ArrayList<>();
    }


    @Override
    public Object clone() {
        Client clone = new Client(pid, login, password, name,surname,age);
        clone.listOfAccounts = new ArrayList<>(listOfAccounts.size());
        clone.listOfAccounts.addAll(listOfAccounts);
        return clone;
    }

    public void addAccount(Account account) {
        listOfAccounts.add(account);
    }

}
