package pl.mrs.webappbank.repositories;

import pl.mrs.webappbank.model.users.Person;

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

    public static RepositoryException ValidationError(String message) {
        return new RepositoryException(message, Response.Status.BAD_REQUEST);
    }

    public static RepositoryException Blocked(String message) {
        return new RepositoryException(message, Response.Status.FORBIDDEN);
    }
}
