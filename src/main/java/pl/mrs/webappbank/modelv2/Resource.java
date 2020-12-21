package pl.mrs.webappbank.modelv2;

import java.util.UUID;

public abstract class Resource {
    protected UUID id;
    protected String description;
    protected boolean available;

    public Resource(String description,boolean available) {
        this.description = description;
        this.available = available;
    }

    public void setId(UUID randomUUID) {
        this.id = randomUUID;
    }
    public UUID getId() {
        return id;
    }
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}