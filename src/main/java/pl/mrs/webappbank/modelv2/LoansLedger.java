package pl.mrs.webappbank.modelv2;

import lombok.Data;
import pl.mrs.webappbank.modelv2.accounts.Account;


import java.util.Date;
import java.util.UUID;

@Data
public class LoansLedger extends Event{
    private Account account;
    private Loan loan;

    public LoansLedger(Client client, Loan loan) {
        this.client = client;
        this.loan = loan;
    }

    public Loan getLoan() {
        return loan;
    }

    public Client getClient() {
        return client;
    }
}
