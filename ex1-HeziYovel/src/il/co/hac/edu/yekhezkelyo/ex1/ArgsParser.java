package il.co.hac.edu.yekhezkelyo.ex1;

/**
 * This class is responsible for the parsing and validation of the commands received from the user.
 */
public class ArgsParser {

    /**
     * The index of the op-code in a split command.
     */
    public static final int ARG_OPCODE = 0;
    /**
     * The index of the URL in a split command.
     */
    public static final int ARG_URL = 1;
    /**
     * The index of the first optional argument in a split command, if provided.
     */
    public static final int ARG_PARAM_0 = 2;

    /**
     * Parses the op-code in the command.
     *
     * @param args the command.
     * @return the op-code.
     * @throws InvalidCommandException thrown if the command does not contain a single char for op-code (or does not
     *         contain an op-code at all).
     */
    public static char parseOpCode(String[] args) {
        try {
            if (args[ARG_OPCODE].length() != 1) {
                throw new InvalidCommandException();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidCommandException();
        }
        return args[ARG_OPCODE].charAt(0);
    }

    /**
     * Parses the URL in the command.
     *
     * @param args the command.
     * @return the URL from the command.
     * @throws InvalidCommandException thrown if the command does not contain a URL.
     */
    public static String parseUrl(String[] args) {
        try {
            return args[ARG_URL];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidCommandException();
        }
    }

    /**
     * Parses an argument from the command. This function assumes that if it was called then at least
     * {@code argIndex + 1} arguments are expected, and throws an {@link InvalidCommandException}
     * if {@code argIndex + 1} arguments are not provided.
     *
     * @param args the command.
     * @param argIndex the index of the required command, aligned to the first argument after the URL.
     * @return the {@code argIndex + 1}'th argument from the command.
     * @exception InvalidCommandException thrown if the command does not contain {@code argIndex + 1} arguments.
     */
    public static String parseArg(String[] args, int argIndex) {
        try {
            return args[ARG_PARAM_0 + argIndex];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidCommandException();
        }
    }
}
