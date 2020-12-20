package pl.mrs.webappbank.managers;

import pl.mrs.webappbank.modelv2.Client;
import pl.mrs.webappbank.modelv2.Loan;
import pl.mrs.webappbank.modelv2.LoansLedger;
import pl.mrs.webappbank.repositories.LoansLedgerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.List;

@ApplicationScoped
@Named
public class LoansLedgerManager {
    private final LoansLedgerRepository loansLedgerRepository;

    public LoansLedgerManager() {
        this.loansLedgerRepository = new LoansLedgerRepository();
    }

    public boolean takeLoan(Loan loan, Client client) {
        if (loan.isAvailable() /*&& client.isActive()*/) {
            loan.setAvailable(false);
            LoansLedger ledger = new LoansLedger(client, loan);
            loansLedgerRepository.add(ledger);
            return true;
        }
        return false;
    }

    public boolean payLoan(Loan loan) {
        if (!loan.isAvailable()) {
            loan.setAvailable(true);
            loansLedgerRepository.payLoan(loansLedgerRepository.findLedgerByLoan(loan));
            return true;
        }
        return false;
    }

    public List<LoansLedger> getAll() { return loansLedgerRepository.findAll(); }
}
