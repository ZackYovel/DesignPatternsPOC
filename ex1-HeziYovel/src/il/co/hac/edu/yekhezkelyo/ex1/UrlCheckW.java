package il.co.hac.edu.yekhezkelyo.ex1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Given a path to a file with words in it (separated by spaces and new lines)
 * this class's instance will check if the given URL contains all of the words in the file.
 */
public class UrlCheckW implements UrlCheck {

    /**
     * The index of the path argument in the list of arguments expected by the word command.
     */
    public static final int ARG_PATH = 0;
    /**
     * This string is used to double-dispatch a type checker to assert that the provided url is textual.
     */
    public static final String ARG_TYPE_PREFIX_TEXT = "text/";
    /**
     * Recognizes any sequence of spaces, tabs and/or new line characters.
     */
    public static final String REGEX_SPLIT_DELIMITER = "\s+";
    /**
     * Recognizes any characters that are not wanted in the text, such as punctuation.
     */
    public static final String REGEX_BAD_CHARACTERS = "[^\\w\\-\s']";
    /**
     * The URL to check.
     */
    private String _url;
    /**
     * A path to a file that should contain a set of words separated by spaces and new lines.
     */
    private String _dictFilePath;

    /**
     * Registers this implementation as a checker for the {@code w} op-code in {@link UrlCheckFactory}.
     * This needs to be called somewhere in the execution flow.
     */
    public static void register() {
        UrlCheckFactory.register(OpCodes.OP_WORD, UrlCheckW::new);
    }

    /**
     * Checks if {@code _url} contains all of the words from the file at {@code _dictFilePath}.
     *
     * @return true if {@code _url} contains all of the words from the file, else false.
     * @throws IOException if {@code _url} is not reachable or not readable.
     */
    @Override
    public boolean checkUrl(String[] args) throws IOException {
        parseCommand(args);

        boolean result = false;

        if (isUrlTextual()) {
            Set<String> words = getWords();

            Set<String> content = getContent();

            result = content.containsAll(words);
        }

        return result;
    }

    /**
     * Uses a content type checker to check if {@code _url} contains text.
     *
     * Since this function is called from the local checkUrl function, this is a use of the double-dispatch design
     * pattern.
     *
     * @return true if {@code _url} contains text, else false.
     * @throws IOException if {@code _url} is not reachable or not readable.
     */
    private boolean isUrlTextual() throws IOException {
        UrlCheck contentTypeCheck = UrlCheckFactory.getCheckFor(OpCodes.OP_CONTENT_TYPE);

        // Double-dispatch to check that URL is textual:
        String[] mockCommand = {String.valueOf(OpCodes.OP_CONTENT_TYPE), _url, ARG_TYPE_PREFIX_TEXT};
        return contentTypeCheck.checkUrl(mockCommand);
    }

    /**
     * Parses the command.
     *
     * @param args the command entered by the user.
     */
    private void parseCommand(String[] args) {
        _url = ArgsParser.parseUrl(args);
        _dictFilePath = ArgsParser.parseArg(args, ARG_PATH);
    }

    /**
     * Fetches the web page from {@code _url} and returns it's text as a set of words.
     *
     * @return the contents of {@code _url} as a set of words.
     * @throws IOException if {@code _url} is not reachable or not readable.
     */
    private Set<String> getContent() throws IOException {
        Document doc = Jsoup.connect(_url).get();
        String lowerCase = doc.text().toLowerCase();
        String punctuationRemoved = lowerCase.replaceAll(REGEX_BAD_CHARACTERS, "");
        String[] words = punctuationRemoved.split(REGEX_SPLIT_DELIMITER);
        return new HashSet<>(Arrays.asList(words));
    }

    /**
     * Fetches {@code _dictFilePath} and returns it's contents as a set of words.
     *
     * @return the contents of {@code _dictFilePath} as a set of words.
     * @throws FileNotFoundException if {@code _dictFilePath} is not found.
     */
    private Set<String> getWords() throws FileNotFoundException {
        Set<String> words = new HashSet<>();
        Scanner scanner = new Scanner(new File(_dictFilePath));
        while (scanner.hasNext()) {
            words.addAll(Arrays.asList(scanner.nextLine().toLowerCase().split(REGEX_SPLIT_DELIMITER)));
        }
        return words;
    }
}
