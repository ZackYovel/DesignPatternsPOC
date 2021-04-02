package il.co.hac.edu.yekhezkelyo.ex1;

import java.util.Scanner;

/**
 * The controller is responsible for the execution flow of the program and I/O.
 */
public class Controller {

    /**
     * Information to print when the program starts.
     */
    private static final String INFO = "ex1-HeziYovel (C) 2021 066733700\nPlease enter commands. Enter 'q' to quit.";
    /**
     * Used to identify an exception that is due to a syntactically wrong URL.
     */
    private static final String MALFORMED_URL = "Malformed URL";
    /**
     * The message displayed to the user when a malformed URL is given.
     */
    private static final String BAD_URL = "bad url";
    /**
     * A general purpose error message for any non-specified error.
     */
    private static final String ERROR = "error";
    /**
     * Regex that recognizes any sequence of spaces, tabs and/or new line characters.
     */
    public static final String DELIMITER_WHITESPACE = "\s+";

    /**
     * A scanner for standard input to get input from the user.
     */
    private final Scanner _scanner = new Scanner(System.in);

    /**
     * The execution flow of the program.
     */
    public void run() {
        System.out.println(INFO);

        String[] args;
        char opCode;

        while (true) {
            try {
                args = getCommand();
                opCode = ArgsParser.parseOpCode(args);

                if (opCode == OpCodes.OP_QUIT) {
                    break;
                }

                UrlCheck check = UrlCheckFactory.getCheckFor(opCode);

                System.out.println(check.checkUrl(args));
            } catch (IllegalArgumentException e) {
                if (e.getMessage().startsWith(MALFORMED_URL)) {
                    System.err.println(BAD_URL);
                } else {
                    e.printStackTrace();
                }
            } catch (Exception e) {
//                e.printStackTrace();
                System.err.println(ERROR);
            }
        }
    }

    /**
     * Get a command from the user. A command is returned as an array of strings. The first
     * is expected to be a single character - the op-code. The second is a URL. An optional variable amount of
     * arguments is sent to the requested operation (the URL is formally also optional, but it must be sent for all
     * commands except the quit command).
     *
     * @return String[]
     */
    private String[] getCommand() {
        String input = _scanner.nextLine();
        return input.split(DELIMITER_WHITESPACE);
    }
}
