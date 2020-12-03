package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.managers.AccountManager;
import pl.mrs.webappbank.managers.ClientManager;
import pl.mrs.webappbank.model.Currency;
import pl.mrs.webappbank.modelv2.Client;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

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
