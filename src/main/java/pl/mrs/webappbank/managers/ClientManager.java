package pl.mrs.webappbank.managers;

import pl.mrs.webappbank.model.users.Client;
import pl.mrs.webappbank.repositories.PersonRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Path("model.client")
public class ClientManager implements Serializable {

    private final PersonRepository personRepository;

    public ClientManager(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public ClientManager() {
        this.personRepository = new PersonRepository();
        //Sample data
        Client c1 = new Client("destroyer11111", "1234", "Jan","Błaszczyk",18);
        Client c2 = new Client("qwerty", "567", "Ziomson","PL",12);
        Client c3 = new Client("azerty", "666", "Janusz","Pawlak",8);
        addClient(c1);
        addClient(c2);
        addClient(c3);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void addClient(Client client) {
            personRepository.add(client);
    }

    public String validateClientData(Client client) {
        return personRepository.clientValidation(client);
    }

    public void removeClient(Client client) {
        personRepository.remove(client);
    }

    @POST
    @Path("{uuid}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void updateClient(@PathParam("uuid") String uuid, Client client) {
        personRepository.updateClient(UUID.fromString(uuid), client);
    }

    @DELETE
    @Path("{login}")
    public void removeByLogin(@PathParam("login") String login) {
        removeClient(findByLogin(login));
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

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Client> getAllClients() {
        return personRepository.findAllClients();
    }

    @GET
    @Path("{login}")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized Client findByLogin(@PathParam("login") String login) {
        return (Client) personRepository.findClientByLogin(login);
    }
}
