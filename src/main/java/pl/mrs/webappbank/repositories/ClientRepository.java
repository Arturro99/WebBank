package pl.mrs.webappbank.repositories;

import pl.mrs.webappbank.model.Currency;
import pl.mrs.webappbank.model.accounts.*;
import pl.mrs.webappbank.modelv2.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements IRepository<Client,String> {
    private List<Client> clients = new ArrayList<>();


    public ClientRepository() {
        this.clients = new ArrayList<>();
        Client c1 = new Client("002020202020", "destroyer69", "1234", "dupa","blada",18);
        Client c2 = new Client("481828218181", "qwerty", "567", "Ziomson","PL",12);
        Client c3 = new Client("156549", "azerty", "666", "JP","dwa",8);
        clients.add(c1);
        clients.add(c2);
        clients.add(c3);

        c1.addAccount(new CommonAccount("123456789", 9990));
        c2.addAccount(new CommonAccount("654312313", 90));
        c2.addAccount(new CurrencyAccount("99876786", 94990, Currency.EUR));
        c3.addAccount(new CommonAccount("6666669954", 850));
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
    public int find(String identifier) {
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
