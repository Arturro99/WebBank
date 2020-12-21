package pl.mrs.webappbank.modelv2;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class LoansLedger extends Event{
    private Client client;
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
