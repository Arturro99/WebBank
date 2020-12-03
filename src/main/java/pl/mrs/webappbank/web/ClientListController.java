package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.managers.AccountManager;
import pl.mrs.webappbank.managers.ClientManager;
import pl.mrs.webappbank.model.Currency;
import pl.mrs.webappbank.modelv2.Client;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named
@Data
public class ClientListController implements Serializable {

    private List<Client> currentClients;

    @Inject
    ClientManager clientManager;

    @Inject
    AccountManager accountManager;

    public String deleteClient(Client c) {
        clientManager.removeClient(c);
        return "AccountList";
    }

    public List<Client> getAllClients() {
        return currentClients;
    }

    @PostConstruct
    public void initController() {
        currentClients = clientManager.getAllClients();

//        Client c1 = new Client("002020202020", "destroyer69", "1234", "dupa","blada",18);
//        Client c2 = new Client("481828218181", "qwerty", "567", "Ziomson","PL",12);
//        Client c3 = new Client("156549", "azerty", "666", "JP","dwa",8);
//        clientManager.addClient(c1);
//        clientManager.addClient(c2);
//        clientManager.addClient(c3);
//        accountManager.registerCurrencyAccount(clientManager.getAllClients().get(0), Currency.PLN);
//        accountManager.registerCurrencyAccount(clientManager.getAllClients().get(1), Currency.EUR);
//        accountManager.registerCommonAccount(clientManager.getAllClients().get(1));
//        accountManager.registerCommonAccount(clientManager.getAllClients().get(2));
//        accountManager.payInto(accountManager.getAllAccounts().get(0).getAccountNumber(),242);
//        accountManager.payInto(accountManager.getAllAccounts().get(1).getAccountNumber(),21522.21);
//        accountManager.payInto(accountManager.getAllAccounts().get(2).getAccountNumber(),12.99);
    }
}
