package pl.mrs.webappbank.managers;

import pl.mrs.webappbank.model.users.Admin;
import pl.mrs.webappbank.model.users.Client;
import pl.mrs.webappbank.model.users.Person;
import pl.mrs.webappbank.repositories.PersonRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ClientManager implements Serializable {

    @Inject
    AccountManager accountManager;

    private final PersonRepository personRepository;

    public ClientManager(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public ClientManager() {
        this.personRepository = new PersonRepository();
    }

    @PostConstruct
    void initAccounts() {
        accountManager.init(getAllClients());
    }


    public void addClient(Client client) {
            personRepository.add(client);
    }

    public String validateClientData(Client client) {
        return personRepository.clientValidation(client);
    }

    public void removeClient(Person person) {
        personRepository.remove(person);
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


    public Person findByLogin(String login) {
        return personRepository.findClientByLogin(login);
    }
    public Client findById(String uuid) {
        return personRepository.findClientByID(UUID.fromString(uuid));
    }
    public Person findByLoginPasswordActive(String login, String password) {
        return personRepository.findByLoginPasswordActive(login,password);
    }

    public Person findByLoginActive(String login) {
        return personRepository.findByLoginActive(login);
    }
}
