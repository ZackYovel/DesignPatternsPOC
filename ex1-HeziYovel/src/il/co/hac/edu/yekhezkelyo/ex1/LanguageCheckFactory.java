package il.co.hac.edu.yekhezkelyo.ex1;

import java.util.HashMap;

/**
 * Factory for {@link LanguageCheck} implementations. To register an implementation, the {@code register()}
 * function must be called. This can be done from the {@code registerAll()} function (by editing it).
 */
public class LanguageCheckFactory {

    /**
     * This map maps language names to commands that generate the appropriate {@link LanguageCheck}
     * implementation instance.
     */
    private static final HashMap<String, GenerateLanguageCheck> generators = new HashMap<>();

    /**
     * When generating an instance of a LanguageCheck, it will be stored here for future use.
     */
    private static final HashMap<String, LanguageCheck> instances = new HashMap<>();

    /**
     * This function has to be called in order to register an implementation. The parameters are the {@code language}
     * to check for, and an object of type {@link GenerateLanguageCheck} which is capable of generating
     * an instance of the implementation on calling {@code execute()}.
     *
     * @param language the language to check for.
     * @param generator a command that generates the implementation.
     */
    public static void register(String language, GenerateLanguageCheck generator){
        generators.put(language, generator);
    }

    /**
     * This function retrieves the registered check for the given language.
     *
     * @param language that a checker for is required.
     * @return a checker for {@code language}.
     * @exception InvalidCommandException thrown if no {@link LanguageCheck} implementation is
     *            registered for {@code language}.
     */
    public static LanguageCheck getCheckFor(String language){
        try {
            if (!instances.containsKey(language)) {
                instances.put(language, generators.get(language).execute());
            }
            return instances.get(language);
        } catch (Exception e) {
            throw new InvalidCommandException();
        }
    }

    /**
     * A command for generating a {@link LanguageCheck} implementation.
     */
    public interface GenerateLanguageCheck {
        /**
         * Calling this should result in generating a {@link LanguageCheck} implementation instance.
         *
         * @return an instance of a {@link LanguageCheck} implementation.
         */
        LanguageCheck execute();
    }
}
