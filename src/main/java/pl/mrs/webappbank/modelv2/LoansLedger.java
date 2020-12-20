package pl.mrs.webappbank.modelv2;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class LoansLedger {
    private UUID uuid;
    private Client client;
    private Loan loan;
    private Date takeLoan;
    private Date payLoan;

    public LoansLedger(Client client, Loan loan) {
        this.takeLoan = new Date();
        this.client = client;
        this.loan = loan;
    }

    public void payLoan() { this.payLoan = new Date(); }
}
