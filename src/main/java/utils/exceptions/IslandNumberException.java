package utils.exceptions;

public class IslandNumberException extends Exception {
    public final static String MESSAGE = "inconsistet number of island with respect to user input. Throwing exception.";

    public IslandNumberException(String message) {
        super(message);
    }
}
