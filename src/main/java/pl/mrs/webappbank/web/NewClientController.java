package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.managers.AccountManager;
import pl.mrs.webappbank.managers.ClientManager;
import pl.mrs.webappbank.modelv2.Client;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ConversationScoped
@Named
@Data
public class NewClientController implements Serializable {

    private Client newClient = new Client();

    @Inject
    ClientManager clientManager;

    @Inject
    AccountManager accountManager;

    @Inject
    private Conversation conversation;

    FacesMessage message;
    FacesContext context;


    public String processNewClient() {
        message = new FacesMessage();
        context = FacesContext.getCurrentInstance();
        conversation.begin();
        if (!passwordValidation(newClient.getPassword())) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password does not match criteria!", null);
            context.addMessage(null, message);
            conversation.end();
            return null;
        }
        if (!loginValidation(newClient.getLogin())) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login already exists!", null);
            context.addMessage(null, message);
            conversation.end();
            return null;
        }
        if (!ageValidation(newClient.getAge())) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect age!", null);
            context.addMessage(null, message);
            conversation.end();
            return null;
        }
        if (!nameValidation(newClient.getName()) || !nameValidation(newClient.getSurname())) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect name or surname!", null);
            context.addMessage(null, message);
            conversation.end();
            return null;
        }
        return "NewClientConfirm";
    }

    public String confirmClient() {
        if (newClient.getLogin() == null) throw new IllegalArgumentException("Nie można przesłać pustego formularza.");
        clientManager.addClient(newClient);
        accountManager.registerCommonAccount(newClient);
        conversation.end();

        return "index";
    }

    private boolean passwordValidation(String password) {
        return password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,32}");
    }
    private boolean loginValidation(String login) {
        return clientManager.getAllClients().stream()
                .map(Client::getLogin)
                .noneMatch(x -> x.equals(login));
    }
    private boolean ageValidation(int age) {
        return age <= 150 && age > 0;
    }
    private boolean nameValidation(String name) {
        return name.matches("(?=.*[a-zA-Z]).{2,}");
    }
}
