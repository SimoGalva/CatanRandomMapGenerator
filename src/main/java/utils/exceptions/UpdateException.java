package utils.exceptions;

public class UpdateException extends Exception {
    public final static String MESSAGE = "Incompatible map key sets, updating failed.";

    public UpdateException(String message) {
        super(message);
    }
}
