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
import java.util.List;

@SessionScoped
@Named
@Data
public class ClientController implements Serializable {

    private Client newClient = new Client();

    private List<Client> currentClients;

    @Inject
    AccountManager accountManager;

    public String processNewClient() {
        return "NewPersonConfirm";
    }

    public String confirmClient() {
        if (newClient.getLogin() == null) throw new IllegalArgumentException("Nie można przesłać pustego formularza.");
        accountManager.registerCommonAccount(newClient);
        newClient = new Client();

        return "index";
    }

    public void deleteClient(Client c) {
        accountManager.removeClient(c);
        initCurrentClients();
    }

    public List<Client> getAllClients() {
        return currentClients;
    }

    @PostConstruct
    public void controllerInit(){
        Client c1 = new Client("002020202020", "destroyer69", "1234", "dupa","blada",18);
        Client c2 = new Client("481828218181", "qwerty", "567", "Ziomson","PL",12);
        Client c3 = new Client("156549", "azerty", "666", "JP","dwa",8);
        accountManager.registerCurrencyAccount(c1, Currency.PLN);
        accountManager.registerCurrencyAccount(c2, Currency.EUR);
        accountManager.registerCommonAccount(c2);
        accountManager.registerCommonAccount(c3);
        accountManager.payInto(accountManager.getAllAccounts().get(0).getAccountNumber(),242);
        accountManager.payInto(accountManager.getAllAccounts().get(1).getAccountNumber(),21522.21);
        accountManager.payInto(accountManager.getAllAccounts().get(2).getAccountNumber(),12.99);
    }

    @PostConstruct
    public void initCurrentClients() {
        currentClients = accountManager.getAllClients();
    }
}
