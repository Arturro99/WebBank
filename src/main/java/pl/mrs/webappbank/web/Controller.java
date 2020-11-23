package pl.mrs.webappbank.web;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import pl.mrs.webappbank.model.Client;
import pl.mrs.webappbank.repositories.ClientRepository;

@RequestScoped
@Named
public class Controller implements Serializable{
    
   
    private ClientRepository clientRepo;
    
    private String pid,name,surname;
    private int age;

    public Controller() {
        clientRepo = new ClientRepository();
        clientRepo.add(new Client("002020202020","dupa","blada",18));
        clientRepo.add(new Client("481828218181","Ziomson","PL",12));
    }

    public ClientRepository getClientRepo() {
        return clientRepo;
    }

    
    public String processClientRepo() {
        System.out.println("UDALO SIE");
        clientRepo.add(new Client(pid,name,surname,age));
        
        System.out.println(clientRepo);
        return "NewClientConfirm";
    }
    public String getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAge(int age) {
        this.age = age;
    }
   
}
