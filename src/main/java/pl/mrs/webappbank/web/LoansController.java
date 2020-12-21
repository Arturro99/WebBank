package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.managers.LoanManager;
import pl.mrs.webappbank.managers.LoansLedgerManager;
import pl.mrs.webappbank.modelv2.Loan;
import pl.mrs.webappbank.modelv2.SafeBox;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@SessionScoped
@Named
@Data
public class LoansController implements Serializable {
    private Loan loan;
    private SafeBox safeBox;

    @Inject
    LoanManager loanManager;

    @Inject
    LoansLedgerManager loansLedgerManager;

//    @Inject
//    Conversation conversation;

    boolean toDeletion;
    List<SafeBox> currentSafeBoxes;

    List<Loan> currentLoans;
    HashMap<String, Boolean> editedLoan;

    boolean safeBoxToDeletion;

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
       // conversation.end();

       // return "Loans";
    }
    public void confirmSafeBox() {
        if (safeBoxToDeletion) {
            loanManager.removeSavebox(safeBox);
            initController();
        }
        else {
            //loansLedgerManager.takeLoan(loan);
        }
      //  conversation.end();

        //return "RentBox";
    }

    public List<SafeBox> getAllSafeBoxes() {
        return currentSafeBoxes;
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
    public String deleteSafebox(SafeBox safeBox) {
        this.safeBox  = safeBox;
        if (safeBox.isAvailable()) {
            safeBoxToDeletion = true;
            //conversation.begin();
            return "RentBoxConfirm";
        }
        else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot remove unavailable Safe Box!", null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, message);
        }
        return null;
    }

    @PostConstruct
    public void initController() {
        currentLoans = loanManager.getAllLoans();
        editedLoan = new HashMap<>();
        currentSafeBoxes = loanManager.getAllSafeBoxes();
    }

    public SafeBox getSafeBox() {
        return safeBox;
    }
}
