package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.managers.AccountManager;
import pl.mrs.webappbank.managers.ClientManager;
import pl.mrs.webappbank.model.users.Client;
import pl.mrs.webappbank.model.users.Person;

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

    private Person newClient = new Client();

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
        String messageString = clientManager.validateClientData((Client) newClient);

        if (!messageString.isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageString, null);
            context.addMessage(null, message);
            conversation.end();
            return null;
        }
        return "NewClientConfirm";
    }

    public String confirmClient() {
        if (newClient.getLogin() == null) throw new IllegalArgumentException("Nie można przesłać pustego formularza.");
        clientManager.addClient((Client)newClient);
        accountManager.registerCommonAccount((Client) newClient);
        conversation.end();

        return "index";
    }

}
