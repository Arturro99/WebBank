package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.managers.LoansLedgerManager;
import pl.mrs.webappbank.modelv2.Client;
import pl.mrs.webappbank.modelv2.Loan;
import pl.mrs.webappbank.modelv2.LoansLedger;
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
    boolean takeLoan;
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

    public List<LoansLedger> getAll() {
        return loansLedgerManager.getAll(); }

    public String confirmLoan() {
        loansLedgerManager.takeLoan(loan, account, client);
        takeLoan = false;
        return "TakeLoan";
    }

    public String payLoan(Account acc, Loan l) {
        if (acc.getStateOfAccount() >= l.getValue()) {
            getLedgerByAccount(acc).stream()
                    .filter(x -> x.getLoan().getId().equals(l.getId()))
                    .forEach(x -> {
                        x.payLoan();
                        x.getLoan().setAvailable(true);
                    });
            return "TakeLoan";
        }
        else {
            context = FacesContext.getCurrentInstance();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not sufficient funds!", null);
            context.addMessage(null, message);
            return null;
        }
    }

    public List<LoansLedger> getLedgerByAccount(Account acc) {
        return loansLedgerManager.getLedgersByAccount(acc);
    }

    public boolean areAccountAndLoanChosen() {
        return account != null && loan != null;
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

    public boolean matchFilter(LoansLedger ledger) {
        if (filterHistory.equals("") || filteringTypeHistory.equals("")) {
            return true;
        }
        switch (filteringTypeHistory) {
            case "lID":
                if (ledger.getLoan().getId().toString().equals(filterHistory)) {
                    return true;
                }
                break;
            case "lDesc":
                if (ledger.getLoan().getDescription().equals(filterHistory)) {
                    return true;
                }
                break;
            case "aNum":
                if (ledger.getAccount().getAccountNumber().equals(filterHistory)) {
                    return true;
                }
                break;
        }
        return false;
    }

}
