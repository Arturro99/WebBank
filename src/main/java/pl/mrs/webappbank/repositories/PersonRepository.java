package pl.mrs.webappbank.repositories;

import pl.mrs.webappbank.model.accounts.Account;
import pl.mrs.webappbank.model.users.Admin;
import pl.mrs.webappbank.model.users.Client;
import pl.mrs.webappbank.model.users.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PersonRepository implements IRepository<Person, UUID> {
    private final List<Person> people;


    public PersonRepository() {
        people = new ArrayList<>();
        //Sample data
        Client c1 = new Client("destroyer11111", "Aa12341234", "Jan","Błaszczyk",18);
        Client c2 = new Client("qwerty", "Aa123asda34", "Ziomson","Pl",12);
        Client c3 = new Client("azerty", "Aa123412ds4", "Janusz","Pawlak",8);
        Person a1 = new Admin("admin", "Proba666");
        add(a1);
        add(c1);
        add(c2);
        add(c3);
    }

    @Override
    public void add(Person element) {
        element.setId(UUID.randomUUID());
        if(!clientValidation(element).equals("")) {
            throw RepositoryException.ValidationError(element.toString());
        }
        synchronized (people) {
            if (people.stream().noneMatch(x -> x.getLogin().equals(element.getLogin()))) {
                people.add(element);
            } else {
                throw RepositoryException.Conflict(element.toString());
            }
        }
    }

    @Override
    public void remove(Person person) {
        synchronized (people) {
            people.remove(person);
        }
    }

    public void updateClient(String login, Client element) {
        Client updatedClient = (Client)findClientByLogin(login);
        if(null != element.getName())
            updatedClient.setName(element.getName());
        if(null != element.getSurname())
            updatedClient.setSurname(element.getSurname());
//        if(null != element.getLogin())
//            updatedClient.setLogin(element.getLogin());
        if(0 != element.getAge())
            updatedClient.setAge(element.getAge());
        if(null != element.getPassword())
            updatedClient.setPassword(element.getPassword());
        if(!element.getListOfAccounts().isEmpty())
            updatedClient.setListOfAccounts(element.getListOfAccounts());
        updatedClient.setBlocked(element.isBlocked());
    }

    public void assignAccount(Client client, Account newAccount) {
        synchronized (people) {
            Client tmp = (Client) people.get(find(client.getPid()));
            tmp.addAccount(newAccount);
        }
    }

    @Override
    public synchronized List<Person> findAll() {
        return new ArrayList<>(people);
    }

    public synchronized List<Client> findAllClients() {
        ArrayList<Client> result = new ArrayList<>();
        for (Person p : people) {
            if (p.getClass() == Client.class)
                result.add((Client) p);
        }
        return result;
    }

    @Override
    public synchronized int find(UUID identifier) {
        return people.stream()
                .filter(x -> x.getPid().equals(identifier))
                .map(people::indexOf)
                .findFirst().orElse(-5);
    }
    public synchronized Client findClientByID(UUID identifier) {
        Optional<Person> found = people.stream().filter(
                x -> x.getPid().equals(identifier) && x.getClass().equals(Client.class)
        ).findFirst();
        if (found.isPresent()) {
            return (Client)found.get();
        } else {
            throw RepositoryException.NotFound(identifier.toString());
        }
    }

    public synchronized Person findClientByLogin(String login) {
        Optional<Person> found = people.stream()
                .filter(x -> x.getLogin().equals(login))
                .findFirst();
        if (found.isPresent()) {
            return found.get();
        } else {
            throw RepositoryException.NotFound(login);
        }
    }

    @Override
    public synchronized String toString() {
        StringBuilder output = new StringBuilder();
        for (Person c : people)
            output.append(c.toString()).append("\n");
        return output.toString();
    }

    public synchronized void blockClient(Client client) {
        client.setBlocked(true);
    }

    public synchronized void unBlockClient(Client client) {
        client.setBlocked(false);
    }


    private boolean passwordValidation(String password) {
        return password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,32}");
    }

    private synchronized boolean loginValidation(String login) {
        return findAllClients().stream()
                .map(Client::getLogin)
                .noneMatch(x -> x.equals(login));
    }

    private boolean ageValidation(int age) {
        return age <= 150 && age > 0;
    }

    private boolean nameValidation(String name) {
        return name.matches("(?=.*[a-zA-Z]).{2,}");
    }

    public String clientValidation(Person client) {
        String message = "";
        if (!nameValidation(client.getName())) {
            message += "Incorrect name or surname! ";
        }

        if (!loginValidation(client.getLogin())) {
            message += "Login already exists! ";
        }

        if (!passwordValidation(client.getPassword())) {
            message += "Password does not match criteria! ";
        }

        if (!ageValidation(client.getAge())) {
            message += "Incorrect age! ";
        }
        return message;
    }

    public Person findByLoginPasswordActive(String login, String password) {
        Optional<Person> foundPerson = people.stream().filter(x ->
                x.getLogin().equals(login)
                        && x.getPassword().equals(password)
                        && !x.isBlocked()
                        ).findFirst();
        return foundPerson.orElse(null);
    }

    public Person findByLoginActive(String login) {
        Optional<Person> foundPerson = people.stream().filter(x ->
                x.getLogin().equals(login)
                        && !x.isBlocked()
        ).findFirst();
        return foundPerson.orElseThrow(() -> RepositoryException.Blocked("Client is blocked"));
    }
}
