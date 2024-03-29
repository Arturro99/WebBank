package pl.mrs.webappbank.repositories;

import pl.mrs.webappbank.model.events.Event;
import pl.mrs.webappbank.model.events.LoansLedger;
import pl.mrs.webappbank.model.events.SafeBoxRent;
import pl.mrs.webappbank.model.accounts.Account;
import pl.mrs.webappbank.model.resources.Loan;
import pl.mrs.webappbank.model.resources.Resource;
import pl.mrs.webappbank.model.resources.SafeBox;
import pl.mrs.webappbank.model.users.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

public class EventRepository implements IRepository<Event, UUID> {
    final private List<Event> events;
//    final private List<LoansLedger> ledgers;
//    final private List<SafeBoxRent> safeBoxesRents;

    public EventRepository() {
        this.events = new ArrayList<>();
//        this.ledgers = new ArrayList<>();
//        this.safeBoxesRents = new ArrayList<>();
    }

    @Override
    public void add(Event element) {
        if(element.getResource().isAvailable() && !element.getClient().isBlocked()) {
            element.getResource().setAvailable(false);
            element.setUuid(UUID.randomUUID());
            if (element.getResource() instanceof Loan) {
                Loan loan = (Loan) element.getResource();
                Account acc =  ((LoansLedger)element).getAccount();
                acc.setStateOfAccount(acc.getStateOfAccount() + loan.getValue());
            }
            events.add(element);
        }
        else if (element.getClient().isBlocked())
            throw RepositoryException.Blocked("Client is blocked");
        else
            throw RepositoryException.Conflict("Resource already rented");
    }

    public void endEvent(Resource resource, Account account) {
        if (resource == null) {
            throw RepositoryException.NotFound("Resource not found");
        }
        if (!resource.isAvailable()) {
            if (account != null) {
                LoansLedger ledger =  findLedgerByAccount(account).stream()
                        .filter(x -> x.getResource().getId().equals(resource.getId()))
                        .findFirst()
                        .orElseThrow(() -> RepositoryException.NotFound("Loan not found"));
                ledger.endEvent();
                ledger.getResource().setAvailable(true);
                account.setStateOfAccount(account.getStateOfAccount() - ((Loan) resource).getValue());
            }
            else {
                SafeBoxRent rent =  findAllRents().stream()
                        .filter(x -> x.getResource().getId().toString().equals(resource.getId().toString()))
                        .findFirst()
                        .orElseThrow(() -> RepositoryException.NotFound("Box not found"));
                rent.endEvent();
                rent.getResource().setAvailable(true);
            }
        }
        else {
            throw RepositoryException.Conflict("Resource not rented");
        }
    }

    @Override
    public void remove(Event element) {
        synchronized (events) {
            events.remove(element);
        }
    }

    @Override
    public List<Event> findAll() {
        return events;
    }

    public List<SafeBoxRent> findAllRents() {
        synchronized (events) {
            return (events.stream()
                    .filter(e -> e.getClass().equals(SafeBoxRent.class))
                    .map(x -> (SafeBoxRent) x)
                    .collect(Collectors.toList()));
        }
    }

    public List<LoansLedger> findAllLedgers() {
        synchronized (events) {
            return (events.stream()
                    .filter(e -> e.getClass().equals(LoansLedger.class))
                    .map(x -> (LoansLedger) x)
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public int find(UUID identifier) {
        return events.indexOf(events.stream()
                .filter(x -> x.getUuid().equals(identifier))
                .findAny()
                .orElse(null));
    }

    public Event get(UUID identifier) {
        return events.stream()
                .filter(x -> x.getUuid().toString().equals(identifier.toString()))
                .findAny()
                .orElseThrow(() -> RepositoryException.NotFound("Account not found"));
    }

    public LoansLedger findLedgerByLoan(Loan loan) {
        return events.stream()
                .filter(x -> x.getClass().equals(LoansLedger.class))
                .filter(x -> x.getResource().getId().equals(loan.getId()))
                .map(x -> (LoansLedger) x)
                .findFirst()
                .get();
    }

    public List<LoansLedger> findLedgerByAccount(Account account) {
        return events.stream()
                .filter(x -> x.getClass().equals(LoansLedger.class))
                .map(x -> (LoansLedger) x)
                .filter(x -> x.getAccount().getAccountNumber().equals(account.getAccountNumber()))
                .filter(LoansLedger::isActive)
                .collect(Collectors.toList());
    }

//    public void payLoan(LoansLedger loanLedger) {
//        synchronized (events) {
//            loanLedger.endEvent();
//        }
//    }

    public List<SafeBoxRent> findRentByClient(Client c) {
        return events.stream()
                .filter(x -> x.getClass().equals(SafeBoxRent.class))
                .map(x -> (SafeBoxRent) x)
                .filter(x -> x.getClient().getPid().equals(c.getPid()))
                .filter(SafeBoxRent::isActive)
                .collect(Collectors.toList());
    }


    public List<Event> getByResourceId(UUID uuid) {
        return events.stream()
                .filter(x -> x.getResource().getId().toString().toLowerCase().contains(uuid.toString()))
                .collect(Collectors.toList());
    }

    public List<Event> getByResourceDescription(String desc) {
        return events.stream()
                .filter(x -> x.getResource().getDescription().toLowerCase().contains(desc.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Event> getByAccountNumber(String num) {
        return events.stream()
                .filter(x -> x.getClass().equals(LoansLedger.class))
                .map(x -> (LoansLedger) x)
                .filter(x -> x.getAccount().getAccountNumber().contains(num))
                .collect(Collectors.toList());
    }

    public List<Event> getByClientId(String id) {
        return events.stream()
                .filter(x -> x.getClass().equals(SafeBoxRent.class))
                .map(x -> (SafeBoxRent) x)
                .filter(x -> x.getClient().getPid().toString().toLowerCase().contains(id.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Event> getByClientLogin(String login) {
        return events.stream()
                .filter(x -> x.getClass().equals(SafeBoxRent.class))
                .map(x -> (SafeBoxRent) x)
                .filter(x -> x.getClient().getLogin().toLowerCase().contains(login.toLowerCase()))
                .collect(Collectors.toList());
    }

}
