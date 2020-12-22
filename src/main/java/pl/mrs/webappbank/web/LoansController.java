package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.managers.LoanManager;
import pl.mrs.webappbank.managers.LoansLedgerManager;
import pl.mrs.webappbank.modelv2.Loan;
import pl.mrs.webappbank.modelv2.Resource;
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
    private String type = "nic";

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
        type="nic";
        return "Loans";
    }

    public List<Loan> getAllLoans() {
        currentLoans = loanManager.getAllLoans();
        currentSafeBoxes = loanManager.getAllSafeBoxes();
        return currentLoans;
    }

    public String deleteLoan(Loan loan) {
        initController();
        this.loan  = loan;
        type = "loan";
        if (loan.isAvailable()) {
            toDeletion = true;
            return "ResourceConfirm";
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
    public void editSafeBox(SafeBox safeBox){
        try {
            loanManager.editSafeBox(safeBox);
        }
        catch (IndexOutOfBoundsException ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "SafeBox has been already deleted", null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, message);
        }
        initController();
    }
    public void confirmSafeBox() {
        if (safeBoxToDeletion) {
            loanManager.removeSavebox(safeBox);
            type = "nic";
            initController();
        }
        else {
            //loansLedgerManager.takeLoan(loan);
        }
      //  conversation.end();

        //return "RentBox";
    }

    public List<SafeBox> getAllSafeBoxes() {
        currentLoans = loanManager.getAllLoans();
        currentSafeBoxes = loanManager.getAllSafeBoxes();
        return currentSafeBoxes;
    }

    public void setEditable(Resource r) {
        if (r.isAvailable()) {
            if (editedLoan.containsKey(r.getId().toString()) && editedLoan.get(r.getId().toString()))
                editedLoan.replace(r.getId().toString(), false);
            else
                editedLoan.put(r.getId().toString(), true);
        }
        else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot edit unavailable resource!", null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, message);
        }
    }
    public boolean getEditable(Resource r) {
        if (editedLoan.containsKey(r.getId().toString()))
            return editedLoan.get(r.getId().toString());
        return false;
    }
    public String deleteSafebox(SafeBox safeBox) {
        initController();
        this.safeBox  = safeBox;
        type = "safeBox";
        if (safeBox.isAvailable()) {
            safeBoxToDeletion = true;
            //conversation.begin();
            return "ResourceConfirm";
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
        currentSafeBoxes = loanManager.getAllSafeBoxes();
        editedLoan = new HashMap<>();
    }

    public SafeBox getSafeBox() {
        return safeBox;
    }

    public String finishDeletion() {
        toDeletion = false;
        type = "nic";
        return "index";
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
