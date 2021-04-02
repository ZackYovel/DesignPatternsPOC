package il.co.hac.edu.yekhezkelyo.ex1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Checks a URL for the presence of img tags.
 */
public class UrlCheckI implements UrlCheck {

    /**
     * The img tag name used in HTML.
     */
    public static final String TAG = "img";

    /**
     * Registers this implementation as a checker for the {@code i} op-code in {@link UrlCheckFactory}.
     * This needs to be called somewhere in the execution flow.
     */
    public static void register() {
        UrlCheckFactory.register(OpCodes.OP_IMAGE, UrlCheckI::new);
    }

    /**
     * Check the URL for the existence of {@code <img>} tags.
     *
     * @return true if there is at least one {@code <img>} tag in the URL, else false.
     * @throws IOException if the URL is unreachable or unreadable.
     */
    @Override
    public boolean checkUrl(String[] args) throws IOException {
        String url = ArgsParser.parseUrl(args);
        Document doc = Jsoup.connect(url).get();
        return doc.select(TAG).size() > 0;
    }
}
