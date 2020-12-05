package pl.mrs.webappbank.managers;

import lombok.AllArgsConstructor;
import pl.mrs.webappbank.modelv2.Loan;
import pl.mrs.webappbank.repositories.LoanRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
@AllArgsConstructor
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
