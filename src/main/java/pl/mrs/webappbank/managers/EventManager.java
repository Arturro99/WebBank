package pl.mrs.webappbank.managers;

import pl.mrs.webappbank.model.events.Event;
import pl.mrs.webappbank.model.events.LoansLedger;
import pl.mrs.webappbank.model.events.SafeBoxRent;
import pl.mrs.webappbank.model.accounts.Account;
import pl.mrs.webappbank.model.resources.Loan;
import pl.mrs.webappbank.model.resources.SafeBox;
import pl.mrs.webappbank.model.users.Client;
import pl.mrs.webappbank.repositories.EventRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Named
public class EventManager {
    private final EventRepository eventRepository;
    public EventManager() {
        this.eventRepository = new EventRepository();
    }

    public boolean takeLoan(Loan loan, Account account, Client client) {
        if (loan.isAvailable() && !client.isBlocked()) {
            loan.setAvailable(false);
            LoansLedger ledger = new LoansLedger(account, loan, client);
            eventRepository.add(ledger);
            account.setStateOfAccount(account.getStateOfAccount() + loan.getValue());
            return true;
        }
        return false;
    }
    public boolean rentBox(SafeBox safeBox, Client client){
        if(safeBox.isAvailable() && !client.isBlocked()){
            safeBox.setAvailable(false);
            SafeBoxRent rent = new SafeBoxRent(client, safeBox);
            eventRepository.add(rent);
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
            eventRepository.payLoan(eventRepository.findLedgerByLoan(loan));
            account.setStateOfAccount(account.getStateOfAccount() - loan.getValue());
            return true;
        }
        return false;
    }

    public List<LoansLedger> getAllLedgers() { return eventRepository.findAllLedgers(); }
    public List<LoansLedger> getLedgersByAccount(Account account) {
        return eventRepository.findLedgerByAccount(account);
    }

    public List<SafeBoxRent> getRentsByClient(Client c) {
        return eventRepository.findRentByClient(c);
    }

    public List<SafeBoxRent> getAllBoxRents() {
        return eventRepository.findAllRents();
    }

    public List<Event> getEventsByResourceId(UUID uuid) {
        return eventRepository.getByResourceId(uuid);
    }

    public List<Event> getEventsByResourceDescription(String desc) {
        return eventRepository.getByResourceDescription(desc);
    }

    public List<Event> getEventsByAccountNumber(String num) {
        return eventRepository.getByAccountNumber(num);
    }

    public List<Event> getEventsByClientId(String id) {
        return eventRepository.getByClientId(id);
    }

    public List<Event> getEventsByClientLogin(String login) {
        return eventRepository.getByClientLogin(login);
    }

    public List<Event> getAll() {
        return eventRepository.findAll();
    }
}
