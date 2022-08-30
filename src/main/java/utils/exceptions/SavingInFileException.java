package utils.exceptions;

public class SavingInFileException extends Exception {
    public final static String MESSAGE = "Something went wrong in saving map data. Savings not available.";

    public SavingInFileException(String message) {
        super(message);
    }
}
