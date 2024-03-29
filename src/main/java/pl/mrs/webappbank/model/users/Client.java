package pl.mrs.webappbank.model.users;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.mrs.webappbank.model.accounts.Account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Client extends Person implements Serializable {

    List<Account> listOfAccounts = new ArrayList<>();

    public Client() {
    }

    public Client(String login, String password, String name, String surname, int age) {
        super(name,surname,login,password);
        this.age = age;
    }

    public Client(String login, String password, String name, String surname, int age, boolean blocked) {
        super(name,surname,login,password, blocked);
        this.age = age;
    }

    public void addAccount(Account account) {
        listOfAccounts.add(account);
    }
    public void deleteAccount(Account account){listOfAccounts.remove(account);}

    @Override
    public String toString() {
        return super.toString() + ", age=" + age;
    }

    @Override
    public UUID getPid() {
        return this.pid;
    }

    @Override
    public String[] getAccessLevel() {
        return new String[]{"CLIENTS"};
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
