package pl.mrs.webappbank.repositories;

import pl.mrs.webappbank.model.Client;
import pl.mrs.webappbank.model.accounts.Account;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;

public class ClientRepository implements IRepository<Client,String> {
    private ArrayList<Client> listOfClients;

//    public void addToListOfClients(Client client) {
//        ;
//    }

//    public ArrayList<Client> getListOfClients() { return listOfClients; }

    public ClientRepository() {
        this.listOfClients = new ArrayList<>();
    }

    @Override
    public void add(Client element) {
        listOfClients.add(element);
    }

    @Override
    public void remove(int index) {
        listOfClients.remove(index);
    }
    public void assignAccount(String personalID, Account newAccount){
        listOfClients.get(find(personalID)).addAccount(newAccount);
    }

    @Override
    public ArrayList<Client> getList() {
        ArrayList<Client> clone = new ArrayList<>(listOfClients.size());
        for (Client item : listOfClients) {
            clone.add((Client) item.clone());
        }
        return clone;
    }

    @Override
    public int find(String identifier) {
        int i = 0;
        for(Client item : listOfClients){
            if(item.getPersonalId().equals(identifier))
                return i;
            i++;
        }
        return -1;
    }

    @Override
    public String toString() {
        String output = "";
        for(Client c : listOfClients)
            output += c.toString() + "\n";
        return  output;
    }
}
