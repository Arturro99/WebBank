package pl.mrs.webappbank.repositories;

import pl.mrs.webappbank.modelv2.Currency;
import pl.mrs.webappbank.modelv2.accounts.*;
import pl.mrs.webappbank.modelv2.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientRepository implements IRepository<Client,UUID> {
    private final List<Client> clients = new ArrayList<>();


    public ClientRepository() {

    }

    @Override
    public void add(Client element) {
        synchronized (clients) {
            clients.add(element);
        }
    }

    @Override
    public void remove(Client client) {
        synchronized (clients) {
            clients.remove(client);
        }
    }
    public void assignAccount(Client client, Account newAccount){
        synchronized (clients) {
            clients.get(find(client.getPid())).addAccount(newAccount);
        }
    }

    @Override
    public List<Client> findAll() {
        return new ArrayList<>(clients);
    }

    @Override
    public int find(UUID identifier) {
        return clients.stream()
                .filter(x -> x.getPid().equals(identifier))
                .map(clients::indexOf)
                .findFirst().orElse(-5);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for(Client c : clients)
            output.append(c.toString()).append("\n");
        return output.toString();
    }

    public void blockClient(Client client) { client.setBlocked(true); }
    public void unBlockClient(Client client) { client.setBlocked(false); }
}
