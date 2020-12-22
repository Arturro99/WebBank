package pl.mrs.webappbank.model.events;

import lombok.Data;
import pl.mrs.webappbank.model.users.Client;
import pl.mrs.webappbank.model.resources.SafeBox;

@Data
public class SafeBoxRent extends Event{
    private Client client;

    public SafeBoxRent(Client client, SafeBox safeBox) {
        this.client = client;
        this.resource = safeBox;
    }
    public SafeBox getSafeBox(){
        return (SafeBox) resource;
    }

    public Client getClient() {
        return client;
    }
}
