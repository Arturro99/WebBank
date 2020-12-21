package pl.mrs.webappbank.modelv2;

import lombok.Data;
import pl.mrs.webappbank.modelv2.accounts.Account;


import java.util.Date;
import java.util.UUID;

@Data
public class LoansLedger extends Event{
    private Account account;
    private Loan loan;

    public LoansLedger(Account account, Loan loan) {
        this.account = account;
        this.loan = loan;
    }
}
