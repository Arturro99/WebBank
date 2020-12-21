package pl.mrs.webappbank.repositories;

import pl.mrs.webappbank.modelv2.Client;
import pl.mrs.webappbank.modelv2.Loan;
import pl.mrs.webappbank.modelv2.LoansLedger;
import pl.mrs.webappbank.modelv2.accounts.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class LoansLedgerRepository implements IRepository<LoansLedger, UUID> {
    final private List<LoansLedger> ledgers;

    public LoansLedgerRepository() {
        this.ledgers = new ArrayList<>();
    }

    @Override
    public void add(LoansLedger element) {
        element.setUuid(UUID.randomUUID());
        ledgers.add(element);
    }

    @Override
    public void remove(LoansLedger element) {
        synchronized (ledgers) {
            ledgers.remove(element);
        }
    }

    @Override
    public List<LoansLedger> findAll() {
        synchronized (ledgers) {
            return new ArrayList<>(ledgers);
        }
    }

    @Override
    public int find(UUID identifier) {
        return ledgers.indexOf(ledgers.stream()
                .filter(x -> x.getUuid().equals(identifier))
                .findAny()
                .orElse(null));
    }

    public LoansLedger findLedgerByLoan(Loan loan) {
        return ledgers.stream()
                .filter(x -> x.getLoan().getId().equals(loan.getId()))
                .findFirst()
                .get();
    }

    public List<LoansLedger> findLedgerByAccount(Account account) {
        return ledgers.stream()
                .filter(x -> x.getAccount().getAccountNumber().equals(account.getAccountNumber()))
                .filter(LoansLedger::isActive)
                .collect(Collectors.toList());
    }

    public void payLoan(LoansLedger loanLedger) {
        synchronized (ledgers) {
            loanLedger.endEvent();
        }
    }
}
