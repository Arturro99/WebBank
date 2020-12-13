package pl.mrs.webappbank.managers;

import pl.mrs.webappbank.modelv2.Client;
import pl.mrs.webappbank.repositories.ClientRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;


@ApplicationScoped
public class ClientManager {
    @Inject
    private ClientRepository clientRepository;

    public ClientManager(ClientRepository clientRepository) {
//        this.clientRepository = clientRepository;
    }

    public ClientManager() {
//        clientRepository = new ClientRepository();
    }

    public void addClient(Client client) {
        if (clientRepository.findAll().stream()
                .noneMatch(x -> x.getPid().equals(client.getPid())))
            clientRepository.add(client);
    }

    public void removeClient(Client client) {
        clientRepository.remove(client);
    }

//    private void connectClientAccount(Client client, Account newAccount) {
//        clientRepository.assignAccount(client,newAccount);
//    }

    public String getInfo() {
        return clientRepository.toString();
    }

    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }
}
