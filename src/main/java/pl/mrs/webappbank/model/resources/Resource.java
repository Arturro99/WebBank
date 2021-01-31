package pl.mrs.webappbank.model.resources;

import pl.mrs.webappbank.model.SignableEntity;

import javax.json.bind.annotation.JsonbTransient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.UUID;

public abstract class Resource implements SignableEntity {
    protected UUID id;
    @NotEmpty
    protected String description;
    protected boolean available;

    public Resource() {
    }

    public Resource(String description, boolean available) {
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

    @Override
    @JsonbTransient
    public String getSignablePayload() {
        if(null == id)
            return "";
        return id.toString();
    }
}