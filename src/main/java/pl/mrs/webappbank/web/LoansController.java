package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.managers.LoanManager;
import pl.mrs.webappbank.managers.LoansLedgerManager;
import pl.mrs.webappbank.modelv2.Loan;
import pl.mrs.webappbank.repositories.LoansLedgerRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ConversationScoped
@Named
@Data
public class LoansController implements Serializable {
    private Loan loan;

    @Inject
    LoanManager loanManager;

    @Inject
    LoansLedgerManager loansLedgerManager;

    @Inject
    Conversation conversation;

    List<Loan> currentLoans;

    boolean toDeletion;


    public String processLoan(Loan loan) {
        this.loan = loan;
        if (loan.isAvailable()) {
            toDeletion = false;
            conversation.begin();
            return "LoanConfirm";
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Loan already in use!", null);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, message);
        return null;
    }

    public String confirmLoan() {
        if (toDeletion) {
            loanManager.removeLoan(loan);
            initController();
        }
        else {
            //loansLedgerManager.takeLoan(loan);
        }
        conversation.end();

        return "Loans";
    }

    public List<Loan> getAllLoans() {
        return currentLoans;
    }

    public String deleteLoan(Loan loan) {
        this.loan  = loan;
        if (loan.isAvailable()) {
            toDeletion = true;
            conversation.begin();
            return "LoanConfirm";
        }
        else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot remove unavailable loan!", null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, message);
        }
        return null;
    }

    @PostConstruct
    public void initController() {
        currentLoans = loanManager.getAllLoans();
    }
}
