package pl.mrs.webappbank.web;

import pl.mrs.webappbank.managers.AccountManager;
import pl.mrs.webappbank.managers.ClientManager;
import pl.mrs.webappbank.model.users.Client;
import pl.mrs.webappbank.model.accounts.Currency;
import pl.mrs.webappbank.model.accounts.SavingsType;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class NewAccountController implements Serializable {
    private String selectedAccountType;
    private Client selectedClient;
    @Inject
    ClientManager clientManager;
    @Inject
    AccountManager accountManager;

    public String registerAccount()
    {
        String[] type_details;
        if(selectedAccountType.contains(" "))
            type_details = selectedAccountType.split(" ");
        else
            type_details = new String[]{selectedAccountType};
        switch (type_details[0]){
            case "Common":
                accountManager.registerCommonAccount(selectedClient);
                break;
            case "Currency":
                accountManager.registerCurrencyAccount(selectedClient, Currency.valueOf(type_details[1]));
                break;
            case "Savings":
                accountManager.registerSavingsAccount(selectedClient, SavingsType.valueOf(type_details[1]));
                break;
        }
        return "index";
    }

    public SavingsType[] getSavingsType() {
        return SavingsType.values();
    }
    public Currency[] getCurrencyType(){
        return Currency.values();
    }

    public  void selectClient(Client selectedClient){
        this.selectedClient = selectedClient;
    }
    public Client getSelectedClient() {
        return selectedClient;
    }
    public void setSelectedAccountType(String selectedAccountType) {
        this.selectedAccountType = selectedAccountType;
    }
    public String getSelectedAccountType() {
        return selectedAccountType;
    }
}
