package utils.exceptions;

public class LoadingException extends Exception {
    public final static String MESSAGE = "Something went wrong in loading from stringMap process. Data in file corrupted.";

    public LoadingException(String message) {
        super(message);
    }
}
