package pl.mrs.webappbank.repositories;

import pl.mrs.webappbank.modelv2.Currency;
import pl.mrs.webappbank.modelv2.accounts.*;
import pl.mrs.webappbank.modelv2.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientRepository implements IRepository<Client,UUID> {
    private List<Client> clients = new ArrayList<>();


    public ClientRepository() {

    }

    @Override
    public void add(Client element) {
        //TODO sprawdzenie unikalności z zadbaniem o wielowątkowość
        clients.add(element);
    }

    @Override
    public void remove(Client client) {
        clients.remove(client);
    }
    public void assignAccount(Client client, Account newAccount){
        clients.get(find(client.getPid())).addAccount(newAccount);
    }

    @Override
    public List<Client> findAll() {
        return new ArrayList<>(clients);
    }

    @Override
    public int find(UUID identifier) {
        return clients.indexOf(clients.stream()
                .filter(c -> c.getPid().equals(identifier))
                .findAny()
                .orElse(new Client()));
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for(Client c : clients)
            output.append(c.toString()).append("\n");
        return output.toString();
    }
}
