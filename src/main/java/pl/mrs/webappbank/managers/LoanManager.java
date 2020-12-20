package pl.mrs.webappbank.managers;

import pl.mrs.webappbank.modelv2.Client;
import pl.mrs.webappbank.modelv2.Loan;
import pl.mrs.webappbank.modelv2.LoansLedger;
import pl.mrs.webappbank.repositories.LoanRepository;
import pl.mrs.webappbank.repositories.LoansLedgerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Named
public class LoanManager {
    private final LoanRepository loanRepository;

    public LoanManager() {
        this.loanRepository = new LoanRepository();
    }

    public void addLoan(Loan loan) {
        if (loanRepository.findAll().stream()
                .noneMatch(x -> x.getId().equals(loan.getId())))
            loanRepository.add(loan);
    }

    public void removeLoan(Loan loan) {
        loanRepository.remove(loan);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }
}
