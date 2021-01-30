package pl.mrs.webappbank.managers;

import pl.mrs.webappbank.model.users.Admin;
import pl.mrs.webappbank.model.users.Client;
import pl.mrs.webappbank.model.users.Person;
import pl.mrs.webappbank.repositories.PersonRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ClientManager implements Serializable {

    private final PersonRepository personRepository;

    public ClientManager(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public ClientManager() {
        this.personRepository = new PersonRepository();

    }


    public void addClient(Client client) {
            personRepository.add(client);
    }

    public String validateClientData(Client client) {
        return personRepository.clientValidation(client);
    }

    public void removeClient(Client client) {
        personRepository.remove(client);
    }

    public void updateClient(String login, Client client) {
        personRepository.updateClient(login, client);
    }


    public void manageBlockade(Client client) {
        if (isClientBlocked(client)) {
            personRepository.unBlockClient(client);
        } else {
            personRepository.blockClient(client);
        }
    }

    public boolean isClientBlocked(Client client) {
        Client tmp = (Client) personRepository.findAll().get(personRepository.find(client.getPid()));
        return tmp.isBlocked();
    }


    public String getInfo() {
        return personRepository.toString();
    }


    public List<Client> getAllClients() {
        return personRepository.findAllClients();
    }


    public synchronized Client findByLogin(String login) {
        return (Client) personRepository.findClientByLogin(login);
    }
}
