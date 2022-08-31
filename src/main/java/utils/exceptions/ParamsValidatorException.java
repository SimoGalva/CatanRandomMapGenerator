package utils.exceptions;

public class ParamsValidatorException extends Exception {
    public final static String MESSAGE = "Invalid input params.";

    public ParamsValidatorException(String message) {
        super(message);
    }
}
