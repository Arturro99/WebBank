package pl.mrs.webappbank.modelv2;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class LoansLedger {
    private UUID uuid;
    private Client client;
    private Loan loan;
    private Date loanTakenDate;
    private Date loanPaidDate;
    private boolean active;

    public LoansLedger(Client client, Loan loan) {
        this.loanTakenDate = new Date();
        this.client = client;
        this.loan = loan;
        this.active = true;
    }

    public void payLoan() {
        this.loanPaidDate = new Date();
        this.active = false;
    }
}
