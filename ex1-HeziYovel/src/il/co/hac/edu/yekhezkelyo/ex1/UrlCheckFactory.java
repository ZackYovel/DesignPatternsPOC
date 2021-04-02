package il.co.hac.edu.yekhezkelyo.ex1;

import java.util.HashMap;

/**
 * A factory class for {@link UrlCheck} implementations.
 * <p>
 * To register, an implementation must call the {@code register()} function. This can be done by editing the
 * {@code registerAll()} function.
 */
public class UrlCheckFactory {

    /**
     * This map maps every op-code to a single {@link UrlCheck} generator function, such that for each op-code
     * the factory can generate the required checker object when needed.
     */
    private static final HashMap<Character, GenerateUrlCheck> generators = new HashMap<>();

    /**
     * This map stores the UrlCheck implementation instances after they are generated for future use.
     */
    private static final HashMap<Character, UrlCheck> instances = new HashMap<>();

    /**
     * Call this to register a {@link UrlCheck} implementation.
     *
     * @param opCode    the implementation will be used to check a URL if the op-code is this.
     * @param generator a command that creates and returns an instance of a {@link UrlCheck} implementation.
     */
    public static void register(Character opCode, GenerateUrlCheck generator) {
        generators.put(opCode, generator);
    }

    /**
     * Returns the registered {@link UrlCheck} implementation for the given op-code.
     *
     * @param opCode the command's op-code.
     * @return the registered {@link UrlCheck} implementation.
     * @throws InvalidCommandException thrown if the op-code in the command is not a valid op-code (no such
     *                                 op-code exists).
     */
    public static UrlCheck getCheckFor(char opCode) {
        try {
            if (!instances.containsKey(opCode)) {
                instances.put(opCode, generators.get(opCode).execute());
            }
            return instances.get(opCode);
        } catch (Exception e) {
            throw new InvalidCommandException();
        }
    }

    /**
     * Defines a command that creates and returns a {@link UrlCheck} implementation.
     */
    public interface GenerateUrlCheck {
        /**
         * Calling this should result in generation of a new {@link UrlCheck} implementation instance.
         *
         * @return an instance of an implementation of {@link UrlCheck}.
         */
        UrlCheck execute();
    }
}
