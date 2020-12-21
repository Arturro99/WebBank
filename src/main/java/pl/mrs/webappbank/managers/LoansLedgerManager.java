package pl.mrs.webappbank.managers;

import pl.mrs.webappbank.modelv2.Client;
import pl.mrs.webappbank.modelv2.Loan;
import pl.mrs.webappbank.modelv2.LoansLedger;
import pl.mrs.webappbank.modelv2.accounts.Account;
import pl.mrs.webappbank.repositories.LoansLedgerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

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
            return true;
        }
        return false;
    }

    public boolean payLoan(Loan loan) {
        if (!loan.isAvailable()) {
            loan.setAvailable(true);
//            List<Client> cl = loansLedgerRepository.findAll().stream()
//                             .filter(x -> x.getLoan().getId().equals(loan.getId()))
//                             .map(LoansLedger::getClient)
//                            .collect(Collectors.toList());
//            cl.get(0).payLoan(loan);
            loansLedgerRepository.payLoan(loansLedgerRepository.findLedgerByLoan(loan));
            return true;
        }
        return false;
    }

    public List<LoansLedger> getAll() { return loansLedgerRepository.findAll(); }
    public List<LoansLedger> getLedgersByAccount(Account account) {
        return loansLedgerRepository.findLedgerByAccount(account);
    }
}
