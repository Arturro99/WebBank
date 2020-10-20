package exceptions;

public class NonexistentAccountException extends Exception{
    public NonexistentAccountException(String message) { super(message); }
}
