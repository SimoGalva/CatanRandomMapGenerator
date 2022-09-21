package utils.exceptions;

public class SavingInFileException extends Exception {
    public final static String MESSAGE = "Something went wrong in saving map data. Savings not available.";
    public static final String CONFING_MESSAGE = "Something went wrong in saving confing data. Confing are not synced";

    public SavingInFileException(String message) {
        super(message);
    }
}
