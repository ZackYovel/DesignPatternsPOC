package il.co.hac.edu.yekhezkelyo.ex1;

/**
 * Defines an exception to be thrown when an invalid command is entered.
 */
public class InvalidCommandException extends RuntimeException {

    /**
     * The message to display to the user.
     */
    public static final String MESSAGE = "invalid command";

    /**
     * An exception to be thrown if the command entered by the user is invalid.
     */
    public InvalidCommandException() {
        super(MESSAGE);
    }
}
