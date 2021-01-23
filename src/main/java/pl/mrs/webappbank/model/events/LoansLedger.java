package pl.mrs.webappbank.model.events;

import lombok.Data;
import pl.mrs.webappbank.model.resources.Loan;
import pl.mrs.webappbank.model.accounts.Account;
import pl.mrs.webappbank.model.users.Client;

@Data
public class LoansLedger extends Event{
    private Account account;

    public LoansLedger(Account account, Loan loan, Client client) {
        this.account = account;
        this.resource = loan;
        this.client = client;
    }
    public Loan getLoan(){
        return (Loan) resource;
    }


}
