package il.co.hac.edu.yekhezkelyo.ex1;

import org.reflections.Reflections;

import java.io.IOException;
import java.util.Set;

/**
 * Checks if a URL is in the requested language.
 */
public class UrlCheckL implements UrlCheck, Registrar<LanguageCheck> {

    /**
     * The index of the language argument in the list of arguments expected by the language command.
     */
    public static final int ARG_LANGUAGE = 0;

    /**
     * Registers this implementation as a checker for the {@code t} op-code in {@link UrlCheckFactory}.
     * This needs to be called somewhere in the execution flow.
     */
    public static void register() {
        UrlCheckFactory.register(OpCodes.OP_LANGUAGE, UrlCheckL::new);
    }

    /*
     * Since this class uses a factory of language checks, it must register all of the implementations of
     * LanguageCheck.
     */
    static {
        new Registrar<LanguageCheck>(){}.registerAll(LanguageCheck.class);
    }

    /**
     * Checks if {@code _url} is in written in {@code _language}.
     *
     * @return true if {@code _url} is written in {@code _language}, false if not.
     * @throws IOException if {@code _url} is not reachable or not readable.
     */
    @Override
    public boolean checkUrl(String[] args) throws IOException {
        String url = ArgsParser.parseUrl(args);
        String language = ArgsParser.parseArg(args, ARG_LANGUAGE);
        return LanguageCheckFactory.getCheckFor(language).checkLanguage(url);
    }
}
