package pl.mrs.webappbank.repositories;

import pl.mrs.webappbank.model.accounts.*;
import pl.mrs.webappbank.model.users.Client;
import pl.mrs.webappbank.model.users.Person;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonRepository implements IRepository<Person,UUID> {
    private final List<Person> people;


    public PersonRepository() {
        people = new ArrayList<>();

    }

    @Override
    public void add(Person element) {
        element.setId(UUID.randomUUID());
        synchronized (people) {
            people.add(element);
        }
    }

    @Override
    public void remove(Person person) {
        synchronized (people) {
            people.remove(person);
        }
    }
    public void assignAccount(Client client, Account newAccount){
        synchronized (people) {
            Client tmp = (Client) people.get(find(client.getPid()));
                    tmp.addAccount(newAccount);
        }
    }

    @Override
    public List<Person> findAll() {
        return new ArrayList<>(people);
    }
    public List<Client> findAllClients(){
        ArrayList<Client> result = new ArrayList<>();
        for(Person p : people){
            if(p.getClass() == Client.class)
                result.add((Client)p);
        }
        return result;
    }

    @Override
    public int find(UUID identifier) {
        return people.stream()
                .filter(x -> x.getPid().equals(identifier))
                .map(people::indexOf)
                .findFirst().orElse(-5);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for(Person c : people)
            output.append(c.toString()).append("\n");
        return output.toString();
    }

    public void blockClient(Client client) { client.setBlocked(true); }
    public void unBlockClient(Client client) { client.setBlocked(false); }
}
