package pl.mrs.webappbank.web;

import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pl.mrs.webappbank.managers.AccountManager;
import pl.mrs.webappbank.managers.DataType;
import pl.mrs.webappbank.managers.IAccountManager;
import pl.mrs.webappbank.model.Client;
import pl.mrs.webappbank.model.Currency;
import pl.mrs.webappbank.model.accounts.SavingsType;
import pl.mrs.webappbank.repositories.ClientRepository;

@SessionScoped
@Named
public class Controller implements Serializable{
    
//    @Inject
//    private AccountManager accountManager;
//
//    private String pid,name,surname;
//    private int age;
//
//
//    public Controller() {
//    }
//    @PostConstruct
//    public void controllerInit(){
//            accountManager.registerCurrencyAccount("002020202020", "destroyer69", "1234", "dupa","blada",18, Currency.PLN);
//            accountManager.registerCurrencyAccount("481828218181", "qwerty", "567", "Ziomson","PL",12, Currency.EUR);
//            accountManager.registerCommonAccount("481828218181", "newMan", "4321","Ziomson","PL",12);
//            accountManager.payInto(accountManager.getAllAccounts().get(0).getAccountNumber(),242);
//            accountManager.payInto(accountManager.getAllAccounts().get(1).getAccountNumber(),21522.21);
//            accountManager.payInto(accountManager.getAllAccounts().get(2).getAccountNumber(),12.99);
//    }
//
//    public AccountManager getAccountManager() {
//        return accountManager;
//    }

//    public String processAccountManager() {
//        accountManager.registerCommonAccount(pid,name,surname,age);
//        System.out.println(accountManager.getInfo(DataType.ACCOUNTS));
//        return "AccountList";
//    }
//    public String getPid() {
//        return pid;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getSurname() {
//        return surname;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setPid(String pid) {
//        this.pid = pid;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setSurname(String surname) {
//        this.surname = surname;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
   
}
