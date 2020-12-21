package pl.mrs.webappbank.modelv2;

import lombok.Data;
import pl.mrs.webappbank.modelv2.accounts.Account;

import java.util.Date;
import java.util.UUID;

@Data
public class LoansLedger {
    private UUID uuid;
    private Account account;
    private Loan loan;
    private Date loanTakenDate;
    private Date loanPaidDate;
    private boolean active;

    public LoansLedger(Account account, Loan loan) {
        this.loanTakenDate = new Date();
        this.account = account;
        this.loan = loan;
        this.active = true;
    }

    public void payLoan() {
        this.loanPaidDate = new Date();
        this.active = false;
    }
}
