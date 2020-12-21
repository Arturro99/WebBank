package pl.mrs.webappbank.managers;

import pl.mrs.webappbank.modelv2.*;
import pl.mrs.webappbank.repositories.EventsRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.List;

@ApplicationScoped
@Named
public class LoansLedgerManager {
    private final EventsRepository eventsRepository;
    public LoansLedgerManager() {
        this.eventsRepository = new EventsRepository();
    }

    public boolean takeLoan(Loan loan, Client client) {
        if (loan.isAvailable() && !client.isBlocked()) {
            loan.setAvailable(false);
//            client.addLoan(loan);
            LoansLedger ledger = new LoansLedger(client, loan);
            eventsRepository.add(ledger);
            return true;
        }
        return false;
    }
    public boolean rentBox(SafeBox safeBox, Client client){
        if(safeBox.isAvailable() && !client.isBlocked()){
            safeBox.setAvailable(false);
            SafeBoxRent rent = new SafeBoxRent(client, safeBox);
            eventsRepository.add(rent);
            return true;
        }
        return false;
    }

    public boolean payLoan(Loan loan) {
        if (!loan.isAvailable()) {
            loan.setAvailable(true);
            eventsRepository.payLoan(eventsRepository.findLedgerByLoan(loan));
            return true;
        }
        return false;
    }

    public List<Event> getAll() { return eventsRepository.findAll(); }

    public List<LoansLedger> getAllLedgers() { return eventsRepository.findAllLedgers(); }
    public List<LoansLedger> getLedgersByClient(Client client) {
        return eventsRepository.findLedgerByClient(client);
    }

    public List<SafeBoxRent> getRentsByClient(Client c) {
        return eventsRepository.findRentByClient(c);
    }

    public List<SafeBoxRent> getAllBoxRents() {
        return eventsRepository.findAllRents();
    }
}
