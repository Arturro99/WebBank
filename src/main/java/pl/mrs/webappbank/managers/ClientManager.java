package pl.mrs.webappbank.managers;

import pl.mrs.webappbank.model.users.Client;
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

    public void manageBlockade(Client client) {
        if (isClientBlocked(client)) {
            clientRepository.unBlockClient(client);
        } else {
            clientRepository.blockClient(client);
        }
    }

    public boolean isClientBlocked(Client client) {
        return clientRepository.findAll().get(clientRepository.find(client.getPid())).isBlocked();
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
