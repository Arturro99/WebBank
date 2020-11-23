package pl.mrs.webappbank.exceptions;

public class NonexistentAccountException extends Exception{
    public NonexistentAccountException(String message) { super(message); }
}
