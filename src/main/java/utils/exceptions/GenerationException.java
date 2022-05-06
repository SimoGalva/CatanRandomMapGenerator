package utils.exceptions;

public class GenerationException extends Exception {
    public final static String MESSAGE = "Something went wrong in generation process.";

    public GenerationException(String message) {
        super(message);
    }
}
