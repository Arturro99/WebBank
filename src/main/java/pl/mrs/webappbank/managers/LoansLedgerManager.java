package pl.mrs.webappbank.managers;

import pl.mrs.webappbank.modelv2.*;
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
        if (loan.isAvailable() && !client.isBlocked()) {
            loan.setAvailable(false);
//            client.addLoan(loan);
            LoansLedger ledger = new LoansLedger(client, loan);
            loansLedgerRepository.add(ledger);
            return true;
        }
        return false;
    }
    public boolean rentBox(SafeBox safeBox, Client client){
        if(safeBox.isAvailable() && !client.isBlocked()){
            safeBox.setAvailable(false);
            SafeBoxRent rent = new SafeBoxRent(client, safeBox);
            loansLedgerRepository.add(rent);
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

    public List<Event> getAll() { return loansLedgerRepository.findAll(); }

    public List<LoansLedger> getAllLedgers() { return loansLedgerRepository.findAllLedgers(); }
    public List<LoansLedger> getLedgersByClient(Client client) {
        return loansLedgerRepository.findLedgerByClient(client);
    }

    public List<SafeBoxRent> getRentsByClient(Client c) {
        return loansLedgerRepository.findRentByClient(c);
    }

    public List<SafeBoxRent> getAllBoxRents() {
        return loansLedgerRepository.findAllRents();
    }
}
