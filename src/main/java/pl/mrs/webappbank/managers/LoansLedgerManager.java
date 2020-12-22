package pl.mrs.webappbank.managers;

import pl.mrs.webappbank.model.events.LoansLedger;
import pl.mrs.webappbank.model.events.SafeBoxRent;
import pl.mrs.webappbank.model.accounts.Account;
import pl.mrs.webappbank.model.resources.Loan;
import pl.mrs.webappbank.model.resources.SafeBox;
import pl.mrs.webappbank.model.users.Client;
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

    public boolean takeLoan(Loan loan, Account account, Client client) {
        if (loan.isAvailable() && !client.isBlocked()) {
            loan.setAvailable(false);
            LoansLedger ledger = new LoansLedger(account, loan);
            loansLedgerRepository.add(ledger);
            account.setStateOfAccount(account.getStateOfAccount() + loan.getValue());
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

    public boolean payLoan(Loan loan, Account account) {
        if (!loan.isAvailable()) {
            getLedgersByAccount(account).stream()
                    .filter(x -> x.getLoan().getId().equals(loan.getId()))
                    .forEach(x -> {
                        x.endEvent();
                        x.getLoan().setAvailable(true);
                    });
            loansLedgerRepository.payLoan(loansLedgerRepository.findLedgerByLoan(loan));
            account.setStateOfAccount(account.getStateOfAccount() - loan.getValue());
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
