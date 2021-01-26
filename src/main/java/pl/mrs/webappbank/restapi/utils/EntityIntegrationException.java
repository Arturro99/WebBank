package pl.mrs.webappbank.restapi.utils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class EntityIntegrationException  extends WebApplicationException {
    public EntityIntegrationException(String message, Response.Status status) {
        super(message, status);
    }

    public static Exception integrityBroken(String message) {
        return new EntityIntegrationException(message, Response.Status.CONFLICT);
    }
}
