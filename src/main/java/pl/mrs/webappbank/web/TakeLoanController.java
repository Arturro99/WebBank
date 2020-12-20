package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.managers.LoansLedgerManager;
import pl.mrs.webappbank.modelv2.Client;
import pl.mrs.webappbank.modelv2.Loan;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Data
@Named
public class TakeLoanController implements Serializable {

    @Inject
    LoansLedgerManager loansLedgerManager;

    Client client;
    Loan loan;
    boolean takeLoan;

    public String processLoan() {
        if (loan.isAvailable()) {
            takeLoan = true;
            return "LoanConfirm";
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Loan already in use!", null);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, message);
        return null;
    }

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
