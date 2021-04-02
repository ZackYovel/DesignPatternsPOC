package il.co.hac.edu.yekhezkelyo.ex1;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * Check if a URL's content type header starts with a given string.
 */
public class UrlCheckT implements UrlCheck {

    /**
     * The index of the type argument in the list of arguments expected by the type command.
     */
    public static final int ARG_TYPE_PREFIX = 0;

    /**
     * Registers this implementation as a checker for the {@code l} op-code in {@link UrlCheckFactory}.
     * This needs to be called somewhere in the execution flow.
     */
    public static void register() {
        UrlCheckFactory.register(OpCodes.OP_CONTENT_TYPE, UrlCheckT::new);
    }

    /**
     * Checks if {@code _url}'s content type begins with {@code _type}.
     *
     * @return true if {@code _url}'s content type begins with {@code _type}, else false.
     * @throws IOException if {@code _url} is not reachable or not readable.
     */
    @Override
    public boolean checkUrl(String[] args) throws IOException {
        // To avoid wasting bandwidth, we fetch only the response headers.
        String url = ArgsParser.parseUrl(args);
        String prefix = ArgsParser.parseArg(args, ARG_TYPE_PREFIX);

        Connection connection = Jsoup.connect(url).method(Connection.Method.HEAD);
        Connection.Response response = connection.ignoreContentType(true).execute();
        String contentType = response.contentType();
        return contentType.startsWith(prefix);
    }
}
