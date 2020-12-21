package pl.mrs.webappbank.managers;

import pl.mrs.webappbank.modelv2.*;
import pl.mrs.webappbank.modelv2.accounts.Account;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.List;

@ApplicationScoped
@Named
public class LoansLedgerManager {
    private final Admin.LoansLedgerRepository loansLedgerRepository;
    public LoansLedgerManager() {
        this.loansLedgerRepository = new Admin.LoansLedgerRepository();
    }

    public boolean takeLoan(Loan loan, Account account, Client client) {
        if (loan.isAvailable() && !client.isBlocked()) {
            loan.setAvailable(false);
            LoansLedger ledger = new LoansLedger(account, loan);
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

    public List<LoansLedger> getAllLedgers() { return loansLedgerRepository.findAllLedgers(); }
    public List<LoansLedger> getLedgersByAccount(Account account) {
        return loansLedgerRepository.findLedgerByAccount(account);
    }

    public List<SafeBoxRent> getRentsByClient(Client c) {
        return loansLedgerRepository.findRentByClient(c);
    }

    public List<SafeBoxRent> getAllBoxRents() {
        return loansLedgerRepository.findAllRents();
    }
}
