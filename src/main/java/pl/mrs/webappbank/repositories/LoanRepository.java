package pl.mrs.webappbank.repositories;

import pl.mrs.webappbank.model.resources.Loan;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoanRepository implements IRepository<Loan, UUID>{
    private final List<Loan> loans;

    public LoanRepository() {
        loans = new ArrayList<>();
        Loan loan1 = new Loan("Minimalna pożyczka", 100, true);
        Loan loan2 = new Loan("Mała pożyczka", 1000, true);
        Loan loan3 = new Loan("Duża pożyczka", 10000, true);
        Loan loan4 = new Loan("Wielki kredyt", 100000, true);
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
        synchronized (loans) {
            element.setId(UUID.randomUUID());
            loans.add(element);
        }
    }

    @Override
    public void remove(Loan element) {
        synchronized (loans) {
            loans.remove(element);
        }
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
