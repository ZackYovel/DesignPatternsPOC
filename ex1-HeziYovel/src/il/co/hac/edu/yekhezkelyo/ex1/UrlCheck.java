package il.co.hac.edu.yekhezkelyo.ex1;


import java.io.IOException;

/**
 * Defines an object that checks a URL for something (determined by the implementation).
 */
public interface UrlCheck {

    /**
     * Check the URL. The specific check is determined by the implementation. The URL is expected to be given
     * as an argument to the constructor or to another function.
     * The reason for this is to avoid letting {@link Controller} handle the parsing of the commands.
     * We don't want {@link Controller} to parse the commands because it will have to handle multiple
     * parsing scenarios: some commands have parameters and some does not. By leaving the parsing
     * to the {@link UrlCheck} implementation the logic of determining how to parse the command is
     * rendered unnecessary since every {@link UrlCheck} implementation knows how to parse the command for itself.
     *
     * @param args the arguments entered by the user, including the op-code, the URL and any additional arguments.
     * @return true if the URL passed the check.
     * @throws IOException if URL is un-reachable or un-readable.
     */
    boolean checkUrl(String[] args) throws IOException;
}
