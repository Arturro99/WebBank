package pl.mrs.webappbank.modelv2;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class LoansLedger extends Event{
    private Client client;

    public LoansLedger(Client client, Loan loan) {
        this.client = client;
        this.resource = loan;
    }

    public Loan getLoan() {
        return (Loan) resource;
    }

    public Client getClient() {
        return client;
    }

}
