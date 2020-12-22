package pl.mrs.webappbank.managers;

import pl.mrs.webappbank.model.resources.Loan;
import pl.mrs.webappbank.model.resources.SafeBox;
import pl.mrs.webappbank.repositories.LoanRepository;
import pl.mrs.webappbank.repositories.SafeBoxRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.List;

@ApplicationScoped
@Named
public class LoanManager {
    private final LoanRepository loanRepository;
    private final SafeBoxRepository safeBoxRepository;

    public LoanManager() {
        this.loanRepository = new LoanRepository();
        this.safeBoxRepository = new SafeBoxRepository();
    }

    public void addLoan(Loan loan) {
        if (loanRepository.findAll().stream()
                .noneMatch(x -> x.getId().equals(loan.getId())))
            loanRepository.add(loan);
    }
    public void addSafeBox(SafeBox safeBox) {
        if (safeBoxRepository.findAll().stream()
                .noneMatch(x -> x.getId().equals(safeBox.getId()))
                &&
            safeBoxRepository.findAll().stream()
                .noneMatch(x -> x.getPosition().equals(safeBox.getPosition()))
        )
            safeBoxRepository.add(safeBox);
    }


    public void editLoan(Loan loan) {
        loanRepository.findAll().get(loanRepository.find(loan.getId())).setValue(loan.getValue());
        loanRepository.findAll().get(loanRepository.find(loan.getId())).setDescription(loan.getDescription());
    }
    public void editSafeBox(SafeBox safeBox){
        safeBoxRepository.findAll().get(safeBoxRepository.find(safeBox.getId())).setPosition(safeBox.getPosition());
        safeBoxRepository.findAll().get(safeBoxRepository.find(safeBox.getId())).setDescription(safeBox.getDescription());
    }

    public void removeLoan(Loan loan) {
        loanRepository.remove(loan);
    }
    public void removeSavebox(SafeBox safeBox){safeBoxRepository.remove(safeBox);}

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }
    public List<SafeBox> getAllSafeBoxes(){return safeBoxRepository.findAll();}
}
