package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.managers.ResourceManager;
import pl.mrs.webappbank.model.resources.Loan;
import pl.mrs.webappbank.model.resources.SafeBox;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ConversationScoped
@Named
@Data
public class NewResourceController implements Serializable {
    Loan newLoan = new Loan();
    SafeBox newSafeBox = new SafeBox();


    @Inject
    ResourceManager resourceManager;

    @Inject
    Conversation conversation;

    boolean resourceCreated = false;

    public String processNewLoan() {
        conversation.begin();
        resourceCreated = true;
        return "ResourceConfirm";
    }
    public String processNewBox(){
        conversation.begin();
        resourceCreated = true;
        return "ResourceConfirm";
    }

    public String confirmLoan() {
        resourceManager.add(newLoan);
        conversation.end();
        return "Loans";
    }

    public String confirmSafeBox(){
        resourceManager.add(newSafeBox);
        conversation.end();
        return "Loans";
    }
    public String finishCreation() {
        resourceCreated = false;
        conversation.end();
        return "index";
    }


}
