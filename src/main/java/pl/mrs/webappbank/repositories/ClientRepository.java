package pl.mrs.webappbank.repositories;

import pl.mrs.webappbank.modelv2.Client;
import pl.mrs.webappbank.model.accounts.Account;

import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements IRepository<Client,String> {
    private List<Client> clients = new ArrayList<>();


    public ClientRepository() {
        this.clients = new ArrayList<>();
    }

    @Override
    public void add(Client element) {
        //TODO sprawdzenie unikalności z zadbaniem o wielowątkowość
        clients.add(element);
    }

    @Override
    public void remove(int index) {
        clients.remove(index);
    }
    public void assignAccount(String personalID, Account newAccount){
        clients.get(find(personalID)).addAccount(newAccount);
    }

    @Override
    public List<Client> findAll() {
        return new ArrayList<>(clients);
    }

    @Override
    public int find(String identifier) {
        int i = 0;
        for(Client item : clients){
            if(item.getPid().equals(identifier))
                return i;
            i++;
        }
        return -1;
    }

    @Override
    public String toString() {
        String output = "";
        for(Client c : clients)
            output += c.toString() + "\n";
        return  output;
    }
}
