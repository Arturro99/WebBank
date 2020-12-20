package pl.mrs.webappbank.repositories;

import pl.mrs.webappbank.modelv2.Client;
import pl.mrs.webappbank.modelv2.Loan;
import pl.mrs.webappbank.modelv2.LoansLedger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoanRepository implements IRepository<Loan, UUID>{
    private List<Loan> loans;

    public LoanRepository() {
        loans = new ArrayList<>();
        Loan loan1 = new Loan("Na małpkę", 100, true);
        Loan loan2 = new Loan("Na miasto", 1000, true);
        Loan loan3 = new Loan("Na multiple", 10000, true);
        Loan loan4 = new Loan("0.0014 Sasina", 100000, true);
        loan1.setId(UUID.randomUUID());
        loan2.setId(UUID.randomUUID());
        loan3.setId(UUID.randomUUID());
        loan4.setId(UUID.randomUUID());

        loans.add(loan1);
        loans.add(loan2);
        loans.add(loan3);
        loans.add(loan4);
    }

    @Override
    public void add(Loan element) {
        element.setId(UUID.randomUUID());
        loans.add(element);
    }

    @Override
    public void remove(Loan element) {
        loans.remove(element);
    }

    @Override
    public List<Loan> findAll() {
        return new ArrayList<>(loans);
    }

    @Override
    public int find(UUID identifier) {
        return loans.indexOf(loans.stream()
                .filter(l -> l.getId().equals(identifier))
                .findAny()
                .orElse(null));
    }
}
