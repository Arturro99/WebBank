package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.managers.ResourcesManager;
import pl.mrs.webappbank.managers.EventsManager;
import pl.mrs.webappbank.modelv2.Loan;
import pl.mrs.webappbank.modelv2.SafeBox;

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
public class ResourcesController implements Serializable {
    private Loan loan;
    private SafeBox safeBox;

    @Inject
    ResourcesManager resourcesManager;

    @Inject
    EventsManager eventsManager;

    @Inject
    Conversation conversation;

    List<SafeBox> currentSafeBoxes;

    List<Loan> currentLoans;

    boolean toDeletion;
    boolean safeBoxToDeletion;


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
            resourcesManager.removeLoan(loan);
            initController();
        }
        else {
            //loansLedgerManager.takeLoan(loan);
        }
        conversation.end();

        return "Loans";
    }
    public String confirmSafeBox() {
        if (safeBoxToDeletion) {
            resourcesManager.removeSavebox(safeBox);
            initController();
        }
        else {
            //loansLedgerManager.takeLoan(loan);
        }
        conversation.end();

        return "RentBox";
    }

    public List<Loan> getAllLoans() {
        return currentLoans;
    }
    public List<SafeBox> getAllSafeBoxes() {
        return currentSafeBoxes;
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
    public String deleteSafebox(SafeBox safeBox) {
        this.safeBox  = safeBox;
        if (safeBox.isAvailable()) {
            safeBoxToDeletion = true;
            conversation.begin();
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
        currentLoans = resourcesManager.getAllLoans();
        currentSafeBoxes = resourcesManager.getAllSafeBoxes();
    }

    public SafeBox getSafeBox() {
        return safeBox;
    }
}
