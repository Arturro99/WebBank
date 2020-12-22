package pl.mrs.webappbank.model.events;

import lombok.Data;
import pl.mrs.webappbank.model.resources.Loan;
import pl.mrs.webappbank.model.accounts.Account;

@Data
public class LoansLedger extends Event{
    private Account account;

    public LoansLedger(Account account, Loan loan) {
        this.account = account;
        this.resource = loan;
    }
    public Loan getLoan(){
        return (Loan) resource;
    }


}
