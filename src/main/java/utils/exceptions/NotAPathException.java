package utils.exceptions;

public class NotAPathException extends Exception {
    public final static String MESSAGE_PATH_DONT_EXIST = "The selected path does not exist.";
    public final static String MESSAGE_ISNT_DIRECTORY = "The selected path isn't a directory.";

    public NotAPathException(String message) {
        super(message);
    }
}
