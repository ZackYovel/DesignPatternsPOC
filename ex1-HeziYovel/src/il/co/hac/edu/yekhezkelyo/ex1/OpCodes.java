package il.co.hac.edu.yekhezkelyo.ex1;

/**
 * This is an enumeration of the different op-codes.
 *
 * The reason this is an {@code interface} and not an {@code enum} is that using an {@code enum} against user input
 * requires a fairly elaborate process of conversions between the {@code enum} and the input values. On the other hand,
 * the added value of a Java {@code enum} - being a defined type and thus creating value ranges that are validatable
 * by the compiler, is not needed here, since all this enumeration is used for is getting a value from the user
 * and using it to pull a checker from a factory.
 *
 * A {@code class} is not used simply because it is not needed here. What's left is an {@code interface}, which has
 * the added value of implicitly making all members {@code public static} and {@code final}.
 */
public interface OpCodes {

    /**
     * Used by the user to tell the application to stop executing.
     */
    char OP_QUIT = 'q';
    /**
     * Indicates checking the URLs content type.
     */
    char OP_CONTENT_TYPE = 't';
    /**
     * Indicates checking if a URL contains a set of words.
     */
    char OP_WORD = 'w';
    /**
     * Indicates checking if a URL contains images.
     */
    char OP_IMAGE = 'i';
    /**
     * Indicates checking if a URL is written in a specific language.
     */
    char OP_LANGUAGE = 'l';
}
