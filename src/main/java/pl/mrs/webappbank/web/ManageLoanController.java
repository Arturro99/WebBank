package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.managers.LoansLedgerManager;
import pl.mrs.webappbank.modelv2.*;

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
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Loan taken successfully", null);
                context.addMessage(null, message);
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
        return loansLedgerManager.getAllBoxRents(); }

    public String confirmLoan() {
        loansLedgerManager.takeLoan(loan, client);
        takeLoan = false;
        return "TakeLoan";
    }
    public String confirmRent() {
        loansLedgerManager.rentBox(safeBox, client);
        rentBox = false;
        return "RentBox";
    }

    public String payLoan(Client c, Loan l) {
        getLedgerByClient(c).stream()
                .filter(x -> x.getResource().getId().equals(l.getId()))
                .forEach(x -> {
                x.endEvent();
                x.getResource().setAvailable(true);
        });
        return "TakeLoan";
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

    public List<LoansLedger> getLedgerByClient(Client c) {
        return loansLedgerManager.getLedgersByClient(c);
    }
    public List<SafeBoxRent> getRentByClient(Client c) {
        return loansLedgerManager.getRentsByClient(c);
    }

    public boolean areClientAndLoanChosen() {
        return client != null && loan != null;
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



    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public void setSafeBox(SafeBox safeBox) {
        this.safeBox = safeBox;
    }

    public Loan getLoan() {
        return loan;
    }

    public SafeBox getSafeBox() {
        return safeBox;
    }
}
