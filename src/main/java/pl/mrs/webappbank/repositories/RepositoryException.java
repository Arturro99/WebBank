package pl.mrs.webappbank.repositories;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class RepositoryException extends WebApplicationException {
    public RepositoryException(String message, Response.Status status) {
        super(message, status);
    }

    public static RepositoryException NotFound(String message) {
        return new RepositoryException(message, Response.Status.NOT_FOUND);
    }

    public static RepositoryException Conflict(String message) {
        return new RepositoryException(message, Response.Status.CONFLICT);
    }
}
