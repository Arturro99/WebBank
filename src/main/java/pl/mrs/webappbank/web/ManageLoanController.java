package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.managers.LoanManager;
import pl.mrs.webappbank.managers.EventManager;
import pl.mrs.webappbank.model.events.Event;
import pl.mrs.webappbank.model.events.LoansLedger;
import pl.mrs.webappbank.model.events.SafeBoxRent;
import pl.mrs.webappbank.model.accounts.Account;
import pl.mrs.webappbank.model.resources.Loan;
import pl.mrs.webappbank.model.resources.SafeBox;
import pl.mrs.webappbank.model.users.Client;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Data
@Named
public class ManageLoanController implements Serializable {

    @Inject
    EventManager loansLedgerManager;
    @Inject
    LoanManager loanManager;

    String type = "nic";
    Client client;
    Loan loan;
    Account account;
    SafeBox safeBox;
    boolean takeLoan;
    boolean rentBox;
    FacesMessage message;
    FacesContext context;
    String filteringTypeHistory;
    String filterHistory;

    @PostConstruct
    public void init() {
        this.filteringTypeHistory = "";
        this.filterHistory = "";
    }

    public String processLoan() {
        context = FacesContext.getCurrentInstance();
        type = "loan";
        if (loan.isAvailable()) {
            if (!client.isBlocked()) {
                takeLoan = true;
                return "ResourceConfirm";
            }
            else {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Client is blocked!", null);
                context.addMessage(null, message);
                return null;
            }
        }
        message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Loan is currently in use!", null);
        context.addMessage(null, message);
        return null;
    }
    public String processRent() {
        context = FacesContext.getCurrentInstance();
        type = "safeBox";
        if (safeBox.isAvailable()) {
            if (!client.isBlocked()) {
                rentBox = true;
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Safe Box rent", null);
                context.addMessage(null, message);
                return "ResourceConfirm";
            }
            else {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Client is blocked!", null);
                context.addMessage(null, message);
                return null;
            }
        }
        message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Safe Box is currently in use!", null);
        context.addMessage(null, message);
        return null;
    }

    public List<LoansLedger> getAllLedgers() {
        return loansLedgerManager.getAllLedgers(); }
    public List<SafeBoxRent> getAllBoxRents() {
        return loansLedgerManager.getAllBoxRents();
    }

    public String confirmLoan() {

        if(loanManager.getAllLoans().contains(loan))
            loansLedgerManager.takeLoan(loan, account, client);
        takeLoan = false;
        type = "nic";
        return "TakeLoan";
    }
    public String confirmRent() {
        if(loanManager.getAllSafeBoxes().contains(safeBox))
            loansLedgerManager.rentBox(safeBox, client);
        rentBox = false;
        type = "nic";
        return "RentBox";
    }

    public String payLoan(Account acc, Loan l) {
        if (acc.getStateOfAccount() >= l.getValue()) {
            loansLedgerManager.payLoan(l, acc);
            return "TakeLoan";
        }
        else {
            context = FacesContext.getCurrentInstance();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not sufficient funds!", null);
            context.addMessage(null, message);
            return null;
        }
    }
    public String returnBox(Client c, SafeBox b){
        getRentByClient(c).stream()
                .filter(x -> x.getResource().getId().equals(b.getId()))
                .forEach(x -> {
                    x.endEvent();
                    x.getResource().setAvailable(true);
                });
        return "RentBox";
    }

    public List<LoansLedger> getLedgerByAccount(Account acc) {
        return loansLedgerManager.getLedgersByAccount(acc);
    }
    public List<SafeBoxRent> getRentByClient(Client c) {
        return loansLedgerManager.getRentsByClient(c);
    }

    public boolean areAccountAndLoanChosen() {
        return account != null && loan != null;
    }
    public boolean areClientAndSafeBoxChosen() {
        return client != null && safeBox != null;
    }

    public String applyFilter() {
        if (filterHistory.equals("") || filteringTypeHistory.equals("")) {
            context = FacesContext.getCurrentInstance();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No filter provided!", null);
            context.addMessage(null, message);
            return null;
        }
        return "LoansHistory";
    }

    public boolean matchFilter(Event event) {
        if (filterHistory.equals("") || filteringTypeHistory.equals("")) {
            return true;
        }
        LoansLedger ledger = null;
        SafeBoxRent safeBoxRent = null;
        switch (filteringTypeHistory) {
            case "rID":
                if (event.getResource().getId().toString().contains(filterHistory)) {
                    return true;
                }
                break;
            case "rDesc":
                if (event.getResource().getDescription().contains(filterHistory)) {
                    return true;
                }
                break;
            case "aNum":
            if(event.getClass().equals(LoansLedger.class)) {
                if (((LoansLedger) event).getAccount().getAccountNumber().contains(filterHistory)) {
                    return true;
                     }
            }
                break;
            case "cID":
                if(event.getClass().equals(SafeBoxRent.class)) {
                    safeBoxRent = (SafeBoxRent) event;
                    if (safeBoxRent.getClient().getPid().toString().contains(filterHistory)) {
                        return true;
                    }
                }
                break;
            case "cLog":
                if(event.getClass().equals(SafeBoxRent.class)) {
                    safeBoxRent = (SafeBoxRent) event;
                if (safeBoxRent.getClient().getLogin().contains(filterHistory)) {
                    return true;
                }
                }
                break;
        }
        return false;
    }

    public String finishTaking() {
        takeLoan = false;
        type = "nic";
        return "index";
    }
    public String finishRenting(){
        rentBox = false;
        type = "nic";
        return "index";
    }
    public String setType(String type){
        this.type=type;
        return "NewResource";
    }
    public boolean isLoanType()
    {
        return type.equals("loan");
    }
    public boolean isSafeBoxType()
    {
        return type.equals("safeBox");
    }


}
