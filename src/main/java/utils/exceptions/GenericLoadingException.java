package utils.exceptions;

public class GenericLoadingException extends Exception {
    //classe giocattolo per accorpare le due eccezioni di loading e catcharle assieme se necessario

    public GenericLoadingException(String message) {super(message);}
}
