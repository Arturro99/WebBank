package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.exceptions.NonexistentAccountException;
import pl.mrs.webappbank.managers.AccountManager;
import pl.mrs.webappbank.managers.ClientManager;
import pl.mrs.webappbank.managers.EventManager;
import pl.mrs.webappbank.model.accounts.Currency;
import pl.mrs.webappbank.model.events.LoansLedger;
import pl.mrs.webappbank.model.accounts.Account;
import pl.mrs.webappbank.model.users.Client;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@SessionScoped
@Named
@Data
public class ClientListController extends HttpServlet implements Serializable {

    private List<Client> currentClients;
    private List<Account> currentAccounts;
    private Client client;
    HashMap<String, Boolean> editedClient;
    HashMap<Account, Client> clientAccounts;

    String filteringType;
    String filter;

    FacesMessage message;
    FacesContext context;

    @Inject
    ClientManager clientManager;

    @Inject
    AccountManager accountManager;

    @Inject
    EventManager loansLedgerManager;

    public ClientListController() {

    }

    public String deleteClient(Client c) {
        if (isClientBlocked(c)) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot remove blocked client!", null);
            context = FacesContext.getCurrentInstance();
            context.addMessage(null, message);
            return null;
        }
        else if (clientHasLoan(c)) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot remove client with loan!", null);
            context = FacesContext.getCurrentInstance();
            context.addMessage(null, message);
            return null;
        }
        clientManager.removeClient(c);
        initController();
        return "AccountList";
    }
    public String deleteAccount(Client c, Account a){
        if (!accountHasLoan(a)) {
            try {
                accountManager.removeAccount(c, a);
            } catch (NonexistentAccountException e) {
                System.out.println(e.getMessage());
            }
            initController();
            return "AccountList";
        }
        else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot remove account with loan!", null);
            context = FacesContext.getCurrentInstance();
            context.addMessage(null, message);
            return null;
        }
    }

    public List<Client> getAllClients() {
        return currentClients;
    }
    public List<Account> getAllAccounts() {return currentAccounts; }

    public void editClient(Client c) {
        clientManager.addClient(c);
    }

    public void setEditable(Client c) {
        if (editedClient.containsKey(c.getPid().toString()) && editedClient.get(c.getPid().toString()))
            editedClient.replace(c.getPid().toString(), false);
        else
            editedClient.put(c.getPid().toString(), true);
    }
    public boolean getEditable(Client c) {
        if (editedClient.containsKey(c.getPid().toString()))
            return editedClient.get(c.getPid().toString());
        return false;
    }

    public void manageClientBlockade(Client c) { clientManager.manageBlockade(c); }
    public boolean isClientBlocked(Client c) { return clientManager.isClientBlocked(c); }


    @PostConstruct
    public void initController() {
        addExampleAccounts();
        clientAccounts = new HashMap<>();
        currentClients = clientManager.getAllClients();
        currentAccounts = accountManager.getAllAccounts();
        for (Client currentClient : currentClients) {
            for (int j = 0; j < currentClient.getListOfAccounts().size(); j++) {
                for (Account currentAccount : currentAccounts) {
                    if (currentClient.getListOfAccounts().get(j).getAccountNumber()
                            .equals(currentAccount.getAccountNumber())) {
                        clientAccounts.put(currentAccount, currentClient);
                    }
                }
            }
        }
        editedClient = new HashMap<>();
        filter = "";
        filteringType = "";
    }
    public void addExampleAccounts(){
        if(accountManager.isExampleAccounts())
            return;
        Client c1 = new Client("destroyer11111", "1234", "Jan","Błaszczyk",18);
        Client c2 = new Client("qwerty", "567", "Ziomson","PL",12);
        Client c3 = new Client("azerty", "666", "Janusz","Pawlak",8);
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

    private boolean clientHasLoan(Client c) {
        return c.getListOfAccounts().stream()
                .map(Account::getAccountNumber)
                .anyMatch(
                        loansLedgerManager.getAllLedgers().stream()
                                .filter(LoansLedger::isActive)
                                .map(x -> x.getAccount().getAccountNumber())
                                .collect(Collectors.toSet())
                                ::contains);
    }

    private boolean accountHasLoan(Account acc) {
        return loansLedgerManager.getAllLedgers().stream()
                .filter(LoansLedger::isActive)
                .map(x -> x.getAccount().getAccountNumber())
                .anyMatch(acc.getAccountNumber()::contains);
    }

    public List<Account> getAccountsByClient(Client client) {
        List<Account> tmp = new ArrayList<>();
        for (Map.Entry<Account, Client> entry : clientAccounts.entrySet()) {
            if (Objects.equals(client.getPid(), entry.getValue().getPid())) {
                tmp.add(entry.getKey());
            }
        }
        return tmp;
    }

    public String applyFilter() {
        if (filter.equals("") || filteringType.equals("")) {
            context = FacesContext.getCurrentInstance();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No filter provided!", null);
            context.addMessage(null, message);
            return null;
        }
        return "AccountList";
    }

    public boolean matchFilter(Client client, Account account) {
        if (filter.equals("") || filteringType.equals("")) {
            return true;
        }
        switch (filteringType) {
            case "aNum":
                if (account != null)
                    return account.getAccountNumber().contains(filter);
                else return client.getListOfAccounts().stream().anyMatch(a -> a.getAccountNumber().contains(filter));
            case "cID":
                return client.getPid().toString().contains(filter);
            case "cLog":
                return client.getLogin().contains(filter);
            case "status":
                return (filter.matches("Active") && !client.isBlocked()) ||
                        (filter.matches("Blocked") && client.isBlocked());

        }
        return false;
    }
}
