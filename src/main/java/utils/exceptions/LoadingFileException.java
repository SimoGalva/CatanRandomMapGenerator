package utils.exceptions;

public class LoadingFileException extends GenericLoadingException {
    public final static String MESSAGE = "Something went wrong in loading from file process.";
    public static final String CONFIG_MESSAGE = "Failed to load config data from file: file is corrupted or not found.";

    public LoadingFileException(String message) {
        super(message);
    }
}
