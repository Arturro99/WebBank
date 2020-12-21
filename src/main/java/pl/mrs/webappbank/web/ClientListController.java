package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.exceptions.NonexistentAccountException;
import pl.mrs.webappbank.managers.AccountManager;
import pl.mrs.webappbank.managers.ClientManager;
import pl.mrs.webappbank.modelv2.Client;
import pl.mrs.webappbank.modelv2.Currency;
import pl.mrs.webappbank.modelv2.accounts.Account;
import pl.mrs.webappbank.modelv2.accounts.SavingsType;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

@ViewScoped
@Named
@Data
public class ClientListController implements Serializable {

    private List<Client> currentClients;
    private Client editedClient;

    @Inject
    ClientManager clientManager;

    @Inject
    AccountManager accountManager;

    public ClientListController() {

    }

    public String deleteClient(Client c) {
        if (isClientBlocked(c)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot remove blocked client!", null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, message);
            return null;
        }
        clientManager.removeClient(c);
        return "AccountList";
    }
    public String deleteAccount(Client c, Account a){
        try {
            accountManager.removeAccount(c,a);
        }
        catch (NonexistentAccountException e)
        {
            System.out.println(e.getMessage());
        }
        return "AccountList";
    }

    public List<Client> getAllClients() {
        return currentClients;
    }

    public void editClient(Client c) {
        clientManager.addClient(c);
    }

    public void setEditable(Client c) {
        c.setEditable(!c.isEditable());
    }
    public boolean getEditable(Client c) { return c.isEditable(); }

    public void manageClientBlockade(Client c) { clientManager.manageBlockade(c); }
    public boolean isClientBlocked(Client c) { return clientManager.isClientBlocked(c); }


    @PostConstruct
    public void initController() {
        addExampleAccounts();
        currentClients = clientManager.getAllClients();
    }
    public void addExampleAccounts(){
        if(accountManager.isExampleAccounts())
            return;
        Client c1 = new Client("destroyer69", "1234", "dupa","blada",18);
        Client c2 = new Client("qwerty", "567", "Ziomson","PL",12);
        Client c3 = new Client("azerty", "666", "JP","dwa",8);
        clientManager.addClient(c1);
        clientManager.addClient(c2);
        clientManager.addClient(c3);

        accountManager.registerCommonAccount(clientManager.getAllClients().get(0));
        accountManager.registerCommonAccount(clientManager.getAllClients().get(1));
        accountManager.registerCurrencyAccount(clientManager.getAllClients().get(1),Currency.EUR);
        accountManager.registerCommonAccount(clientManager.getAllClients().get(2));

        accountManager.payInto(accountManager.getAllAccounts().get(0).getAccountNumber(),9990);
        accountManager.payInto(accountManager.getAllAccounts().get(1).getAccountNumber(),90);
        accountManager.payInto(accountManager.getAllAccounts().get(2).getAccountNumber(),94990);
        accountManager.payInto(accountManager.getAllAccounts().get(2).getAccountNumber(),850);
        accountManager.setExampleAccounts(true);
    }

}
