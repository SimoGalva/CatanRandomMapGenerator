package utils.exceptions;

public class LoadingFileException extends GenericLoadingException {
    public final static String MESSAGE = "Something went wrong in loading from file process.";

    public LoadingFileException(String message) {
        super(message);
    }
}
