package pl.mrs.webappbank.modelv2;

import lombok.Data;

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
