package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.managers.LoansLedgerManager;
import pl.mrs.webappbank.modelv2.Client;
import pl.mrs.webappbank.modelv2.Loan;
import pl.mrs.webappbank.modelv2.LoansLedger;

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
public class TakeLoanController implements Serializable {

    @Inject
    LoansLedgerManager loansLedgerManager;

    Client client;
    Loan loan;
    boolean takeLoan;
    FacesMessage message;
    FacesContext context;

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

    public List<LoansLedger> getAll() { return loansLedgerManager.getAll(); }

    public String confirmLoan() {
        loansLedgerManager.takeLoan(loan, client);
        takeLoan = false;
        System.out.println(loansLedgerManager.getAll());
        return "Loans";
    }

    public boolean areClientAndLoanChosen() {
        return client != null && loan != null;
    }

}
