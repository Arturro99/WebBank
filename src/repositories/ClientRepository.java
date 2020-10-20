package repositories;

import model.Client;

import java.util.ArrayList;

public class ClientRepository {
    private ArrayList<Client> listOfClients;

    public void addToListOfClients(Client client) {
        listOfClients.add(client);
    }

    public ArrayList<Client> getListOfClients() { return listOfClients; }
}
