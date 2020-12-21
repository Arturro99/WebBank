package pl.mrs.webappbank.modelv2;

import java.util.Date;
import java.util.UUID;

public abstract class Event {
    protected UUID uuid;
    protected Date startDate;
    protected Date endDate;
    protected boolean active;
    protected Resource resource;

    public Resource getResource() {
        return resource;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isActive() {
        return active;
    }

    public Event() {
        this.startDate = new Date();
        this.active = true;
    }

    public void endEvent() {
        this.endDate = new Date();
        this.active = false;
    }


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}