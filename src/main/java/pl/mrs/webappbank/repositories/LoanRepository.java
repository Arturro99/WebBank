package pl.mrs.webappbank.repositories;

import pl.mrs.webappbank.modelv2.Client;
import pl.mrs.webappbank.modelv2.Loan;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoanRepository implements IRepository<Loan, UUID>{
    private List<Loan> loans;

    public LoanRepository() {
        loans = new ArrayList<>();
        loans.add(new Loan("Na małpkę", 100, true));
        loans.add(new Loan("Na miasto", 1000, true));
        loans.add(new Loan("Na multiple", 10000, true));
        loans.add(new Loan("0.0014 Sasina", 100000, true));
    }

    @Override
    public void add(Loan element) {
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
