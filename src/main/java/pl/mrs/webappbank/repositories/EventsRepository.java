package pl.mrs.webappbank.repositories;

import pl.mrs.webappbank.modelv2.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EventsRepository implements IRepository<Event, UUID> {
    final private List<LoansLedger> ledgers;
    final private List<SafeBoxRent> safeBoxesRents;

    public EventsRepository() {

        this.ledgers = new ArrayList<>();
        this.safeBoxesRents = new ArrayList<>();
    }

    @Override
    public void add(Event element) {
        element.setUuid(UUID.randomUUID());
        if (element.getClass().equals(SafeBoxRent.class))
            safeBoxesRents.add((SafeBoxRent) element);
        else
            ledgers.add((LoansLedger) element);
    }

    @Override
    public void remove(Event element) {

        if (element.getClass().equals(SafeBoxRent.class)) {
            synchronized (safeBoxesRents) {
                safeBoxesRents.remove(element);
            }
        } else {
            synchronized (ledgers) {
                ledgers.remove(element);
            }
        }
    }

    @Override
    public List<Event> findAll() {
        List<Event> events;
        synchronized (ledgers) {
            events = new ArrayList<>(ledgers);
            events.addAll(safeBoxesRents);
        }
        return events;
    }

    public List<SafeBoxRent> findAllRents() {
        synchronized (safeBoxesRents) {
            return new ArrayList<>(safeBoxesRents);
        }
    }

    public List<LoansLedger> findAllLedgers() {
        synchronized (ledgers) {
            return new ArrayList<>(ledgers);
        }
    }

    @Override
    public int find(UUID identifier) {
        int index = -1;
        index = ledgers.indexOf(ledgers.stream()
                .filter(x -> x.getUuid().equals(identifier))
                .findAny()
                .orElse(null));
        if (index < 0) {
            index = safeBoxesRents.indexOf(safeBoxesRents.stream()
                    .filter(x -> x.getUuid().equals(identifier))
                    .findAny()
                    .orElse(null));
        }
        return index;
    }

    public LoansLedger findLedgerByLoan(Loan loan) {
        return ledgers.stream()
                .filter(x -> x.getResource().getId().equals(loan.getId()))
                .findFirst()
                .get();
    }

    public List<LoansLedger> findLedgerByClient(Client client) {
        return ledgers.stream()
                .filter(x -> x.getClient().getPid().equals(client.getPid()))
                .filter(LoansLedger::isActive)
                .collect(Collectors.toList());
    }

    public void payLoan(LoansLedger loanLedger) {
        synchronized (ledgers) {
            loanLedger.endEvent();
        }
    }

    public List<SafeBoxRent> findRentByClient(Client c) {
        return safeBoxesRents.stream()
                .filter(x -> x.getClient().getPid().equals(c.getPid()))
                .filter(SafeBoxRent::isActive)
                .collect(Collectors.toList());
    }
}
