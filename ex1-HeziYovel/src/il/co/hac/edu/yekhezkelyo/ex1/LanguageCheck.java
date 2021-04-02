package il.co.hac.edu.yekhezkelyo.ex1;

import java.io.IOException;

/**
 * Defines a language checking type.
 */
public interface LanguageCheck {
    /**
     * Check the language of the HTML page in url. Return true if it is of the required language (determined by the
     * implementation), else if not.
     *
     * @param url a web address of an HTML page.
     * @return true if the HTML page in {@code url} is of the required language, else if not.
     * @throws IOException if {@code url} cannot be reached or read.
     */
    boolean checkLanguage(String url) throws IOException;
}
