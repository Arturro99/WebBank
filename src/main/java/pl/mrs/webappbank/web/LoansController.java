package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.managers.LoanManager;
import pl.mrs.webappbank.modelv2.Loan;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
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
    Conversation conversation;

    public String processLoan() {
        conversation.begin();
        return "LoanConfirm";
    }

    public String confirmLoan() {
        loan.setAvailable(false);
        conversation.end();

        return "Loans";
    }

    public List<Loan> getAllLoans() {
        return loanManager.getAllLoans();
    }

    public void instantiateLoan(Loan loan) {
        this.loan = loan;
    }

}
