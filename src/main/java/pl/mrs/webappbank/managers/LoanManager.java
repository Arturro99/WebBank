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
import java.util.stream.Collectors;

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

    public void editLoan(Loan loan) {
        loanRepository.findAll().get(loanRepository.find(loan.getId())).setValue(loan.getValue());
        loanRepository.findAll().get(loanRepository.find(loan.getId())).setDescription(loan.getDescription());
    }

    public void removeLoan(Loan loan) {
        loanRepository.remove(loan);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }
}
