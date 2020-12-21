package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.managers.LoansLedgerManager;
import pl.mrs.webappbank.modelv2.*;
import pl.mrs.webappbank.modelv2.accounts.Account;

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
    LoansLedgerManager loansLedgerManager;

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
        if (loan.isAvailable()) {
            if (!client.isBlocked()) {
                takeLoan = true;
                return "LoanConfirm";
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
        if (safeBox.isAvailable()) {
            if (!client.isBlocked()) {
                rentBox = true;
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Safe Box rent", null);
                context.addMessage(null, message);
                return "RentBoxConfirm";
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
        loansLedgerManager.takeLoan(loan, account, client);
        takeLoan = false;
        return "TakeLoan";
    }
    public String confirmRent() {
        loansLedgerManager.rentBox(safeBox, client);
        rentBox = false;
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
                    safeBoxRent = (SafeBoxRent) event;
                if (ledger.getAccount().getAccountNumber().contains(filterHistory)) {
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
        return "index";
    }

}
