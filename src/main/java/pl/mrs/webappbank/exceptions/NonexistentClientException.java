package pl.mrs.webappbank.exceptions;

public class NonexistentClientException extends Exception{
    public NonexistentClientException(String message) { super(message); }
}
