package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.managers.LoanManager;
import pl.mrs.webappbank.modelv2.Loan;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ConversationScoped
@Named
@Data
public class NewLoanController implements Serializable {
    Loan newLoan = new Loan();

    @Inject
    LoanManager loanManager;

    @Inject
    Conversation conversation;

    boolean loanCreated = false;

    public String processNewLoan() {
        conversation.begin();
        loanCreated = true;
        return "LoanConfirm";
    }

    public String confirmLoan() {
        loanManager.addLoan(newLoan);
        conversation.end();
        return "Loans";
    }

    public String finishCreation() {
        loanCreated = false;
        return "index";
    }
}
