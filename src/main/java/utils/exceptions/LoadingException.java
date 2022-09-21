package utils.exceptions;

public class LoadingException extends GenericLoadingException {
    public final static String MESSAGE = "Something went wrong in loading from stringMap process. Data in file corrupted.";
    public final static String MESSAGE_NOT_USABLE_MAP = "Unacceptable number of lines in map. Data in file corrupted.";
    public static final String CONFIG_MESSAGE = "Failed to load data from file content, data may be corrupted. Setting 0 to all parameters.";

    public LoadingException(String message) {
        super(message);
    }
}
