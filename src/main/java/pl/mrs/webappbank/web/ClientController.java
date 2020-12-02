package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.managers.AccountManager;
import pl.mrs.webappbank.model.Currency;
import pl.mrs.webappbank.modelv2.Client;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
@Data
public class ClientController implements Serializable {

    private Client newClient = new Client();

    @Inject
    AccountManager accountManager;

    public String processNewClient() {
        return "NewPersonConfirm";
    }

    public String confirmClient() {
        accountManager.registerCommonAccount(newClient);
        return "index";
    }

    @PostConstruct
    public void controllerInit(){
        Client c1 = new Client("002020202020", "destroyer69", "1234", "dupa","blada",18);
        Client c2 = new Client("481828218181", "qwerty", "567", "Ziomson","PL",12);
        Client c3 = new Client("123654", "newMan", "4321","Tegowy","Januszewicz",15);
        accountManager.registerCurrencyAccount(c1, Currency.PLN);
        accountManager.registerCurrencyAccount(c2, Currency.EUR);
        accountManager.registerCommonAccount(c3);
        accountManager.payInto(accountManager.getAllAccounts().get(0).getAccountNumber(),242);
        accountManager.payInto(accountManager.getAllAccounts().get(1).getAccountNumber(),21522.21);
        accountManager.payInto(accountManager.getAllAccounts().get(2).getAccountNumber(),12.99);
    }
}
