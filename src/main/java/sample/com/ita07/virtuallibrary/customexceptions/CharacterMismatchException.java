package sample.com.ita07.virtuallibrary.customexceptions;
/**
 * Custom Exception Class
 */
public class CharacterMismatchException extends Exception {
    /**
     * Creates an exception with a String message
     * @param errorMessage String value of the error output
     */
    public CharacterMismatchException(String errorMessage) {
        super(errorMessage);
    }
}
