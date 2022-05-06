package utils.exceptions;

public class NoInstanceException extends Exception{
    public final static String MESSAGE = "no instance was created. Returning null; throwing exception.";

    public NoInstanceException(String message) {
        super(message);
    }
}
