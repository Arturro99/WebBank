package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.managers.AccountManager;
import pl.mrs.webappbank.model.Currency;
import pl.mrs.webappbank.modelv2.Client;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
@Data
public class ClientController {

    private Client newClient = new Client();

    @Inject
    AccountManager accountManager;

    public String processNewClient() {
        accountManager.registerCommonAccount(newClient.getPid(), newClient.getLogin(),
                newClient.getPassword(), newClient.getName(), newClient.getSurname(), newClient.getAge());
        return "NewPersonConfirm";
    }

    public String confirmClient() {
        return "index";
    }

    @PostConstruct
    public void controllerInit(){
        accountManager.registerCurrencyAccount("002020202020", "destroyer69", "1234", "dupa","blada",18, Currency.PLN);
        accountManager.registerCurrencyAccount("481828218181", "qwerty", "567", "Ziomson","PL",12, Currency.EUR);
        accountManager.registerCommonAccount("481828218181", "newMan", "4321","Ziomson","PL",12);
        accountManager.payInto(accountManager.getAllAccounts().get(0).getAccountNumber(),242);
        accountManager.payInto(accountManager.getAllAccounts().get(1).getAccountNumber(),21522.21);
        accountManager.payInto(accountManager.getAllAccounts().get(2).getAccountNumber(),12.99);
    }
}
