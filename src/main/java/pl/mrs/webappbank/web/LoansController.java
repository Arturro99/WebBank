package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.managers.ClientManager;
import pl.mrs.webappbank.managers.LoanManager;
import pl.mrs.webappbank.managers.LoansLedgerManager;
import pl.mrs.webappbank.modelv2.Client;
import pl.mrs.webappbank.modelv2.Loan;
import pl.mrs.webappbank.modelv2.accounts.Account;
import pl.mrs.webappbank.repositories.LoansLedgerRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@SessionScoped
@Named
@Data
public class LoansController implements Serializable {
    private Loan loan;

    @Inject
    LoanManager loanManager;

    @Inject
    LoansLedgerManager loansLedgerManager;

//    @Inject
//    Conversation conversation;

    boolean toDeletion;
    List<Loan> currentLoans;
    HashMap<String, Boolean> editedLoan;


    public String confirmDeletion() {
        loanManager.removeLoan(loan);
        toDeletion = false;
        initController();
        return "Loans";
    }

    public List<Loan> getAllLoans() {
        return currentLoans;
    }

    public String deleteLoan(Loan loan) {
        initController();
        this.loan  = loan;
        if (loan.isAvailable()) {
            toDeletion = true;
            return "LoanConfirm";
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot remove unavailable loan!", null);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, message);
        return null;
    }

    public void editLoan(Loan loan) {
        try {
            loanManager.editLoan(loan);
        }
        catch (IndexOutOfBoundsException ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Loan has been already deleted", null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, message);
        }
        initController();
    }

    public void setEditable(Loan l) {
        if (l.isAvailable()) {
            if (editedLoan.containsKey(l.getId().toString()) && editedLoan.get(l.getId().toString()))
                editedLoan.replace(l.getId().toString(), false);
            else
                editedLoan.put(l.getId().toString(), true);
        }
        else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot edit unavailable loan!", null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, message);
        }
    }
    public boolean getEditable(Loan l) {
        if (editedLoan.containsKey(l.getId().toString()))
            return editedLoan.get(l.getId().toString());
        return false;
    }

    @PostConstruct
    public void initController() {
        currentLoans = loanManager.getAllLoans();
        editedLoan = new HashMap<>();
    }
}
