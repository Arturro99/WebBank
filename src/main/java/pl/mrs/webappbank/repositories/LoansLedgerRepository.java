package pl.mrs.webappbank.repositories;

import pl.mrs.webappbank.modelv2.Loan;
import pl.mrs.webappbank.modelv2.LoansLedger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class LoansLedgerRepository implements IRepository<LoansLedger, UUID> {
    private List<LoansLedger> loans;

    public LoansLedgerRepository() {
        this.loans = new ArrayList<>();
    }

    @Override
    public void add(LoansLedger element) {
        element.setUuid(UUID.randomUUID());
        loans.add(element);
    }

    @Override
    public void remove(LoansLedger element) {
        synchronized (loans) {
            loans.remove(element);
        }
    }

    @Override
    public List<LoansLedger> findAll() {
        synchronized (loans) {
            return new ArrayList<>(loans);
        }
    }

    @Override
    public int find(UUID identifier) {
        return loans.indexOf(loans.stream()
                .filter(x -> x.getUuid().equals(identifier))
                .findAny()
                .orElse(null));
    }

    public LoansLedger findLedgerByLoan(Loan loan) {
        return loans.stream()
                .filter(x -> x.getLoan().getId().equals(loan.getId()))
                .findFirst()
                .get();
    }

    public void payLoan(LoansLedger loanLedger) {
        synchronized (loans) {
            loanLedger.payLoan();
        }
    }
}
