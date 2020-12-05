package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.managers.AccountManager;
import pl.mrs.webappbank.managers.ClientManager;
import pl.mrs.webappbank.modelv2.Client;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
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


    public String processNewClient() {
        conversation.begin();
        return "NewClientConfirm";
    }

    public String confirmClient() {
        if (newClient.getLogin() == null) throw new IllegalArgumentException("Nie można przesłać pustego formularza.");
        clientManager.addClient(newClient);
        accountManager.registerCommonAccount(newClient);
        conversation.end();

        return "index";
    }
}
