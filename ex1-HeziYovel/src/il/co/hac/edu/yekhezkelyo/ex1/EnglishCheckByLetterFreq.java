package il.co.hac.edu.yekhezkelyo.ex1;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;

/**
 * Checks if an HTML page in a given address is in English, based on letter frequencies.
 */
public class EnglishCheckByLetterFreq implements LanguageCheck {

    /**
     * Array indexes are ASCII values of small letters minus value of 'a',
     * which aligns them to 0 ('a' - 'a' == 0, 'b' - 'a' == 1,...).
     */
    private static final double[] _frequencyTable = {
            0.08167 /*a*/, 0.01492 /*b*/, 0.02782 /*c*/, 0.04253 /*d*/,
            0.12702 /*e*/, 0.02228 /*f*/, 0.02015 /*g*/, 0.06094 /*h*/,
            0.06966 /*i*/, 0.00153 /*j*/, 0.00772 /*k*/, 0.04025 /*l*/,
            0.02406 /*m*/, 0.06749 /*n*/, 0.07507 /*o*/, 0.01929 /*p*/,
            0.00095 /*q*/, 0.05987 /*r*/, 0.06327 /*s*/, 0.09056 /*t*/,
            0.02758 /*u*/, 0.00978 /*v*/, 0.02360 /*w*/, 0.00150 /*x*/,
            0.01974 /*y*/, 0.00074 /*z*/
    };

    /**
     * This constant determines how "sensitive" the algorithm is, or how likely it is that the
     * URL is indeed written in English. The lower it is, the higher the probability that that URL is
     * written in English, but it may generate a higher amount of false-negatives. The higher the threshold,
     * the lower the amount of false-negatives, but also the higher the amount of false-positives. So
     * this constant may require fine-tuning whenever the algorithm changes.
     */
    private static final double THRESHOLD = 0.007;
    /**
     * Small a character used for character arithmetics.
     */
    public static final char SMALL_A = 'a';
    /**
     * Small z character used for character arithmetics.
     */
    public static final char SMALL_Z = 'z';
    /**
     * The number 2 to use as an exponent in the computation of the variance.
     */
    public static final int EXPONENT_2 = 2;
    /**
     * Used when a character is met for the first time and does not exist in the counts yet.
     */
    public static final int DEFAULT_COUNT = 0;
    /**
     * Defines how much to add to the count when a character is met.
     */
    public static final int COUNT_INCREMENT = 1;
    /**
     * A general-purpose constant 0.
     */
    public static final int ZERO = 0;
    /**
     * The difference between the index of the last cell of an array to the array's size, used to compute the size
     * of an array by subtracting indexes of items.
     */
    public static final int INDEX_TO_SIZE_OFFSET = 1;
    /**
     * Key to the LanguageCheckFactory to register this implementation by.
     */
    public static final String ENGLISH_FACTORY_KEY = "english";

    /**
     * This should be called in order to register this check in the {@link LanguageCheckFactory}.
     */
    public static void register() {
        LanguageCheckFactory.register(ENGLISH_FACTORY_KEY, EnglishCheckByLetterFreq::new);
    }

    /**
     * Attempts to "guess" the language of url based on letter frequencies.
     *
     * @param url a web address of an HTML page.
     * @return true if the text of url is English in high probability, else false.
     * @throws IOException if url is not reachable or not readable.
     */
    @Override
    public boolean checkLanguage(String url) throws IOException {
        String text = Jsoup.connect(url).get().text().toLowerCase();

        HashMap<Character, Integer> charCounts = getCharCounts(text);

        double[] textFrequencies = computeLetterFrequencies(text, charCounts);

        double variance = computeVariance(textFrequencies);

        return variance < THRESHOLD;
    }

    /**
     * Given a {@code HashMap<Character, Integer>} of the number of times each letter is in the file,
     * computes an array of frequencies (probabilities) by dividing each count by the total length of the text.
     *
     * @param text       the text of the file being checked
     * @param charCounts the number of time each letter is in text
     * @return an array with the frequencies of each letter
     */
    private double[] computeLetterFrequencies(String text, HashMap<Character, Integer> charCounts) {
        double[] textFrequencies = new double[SMALL_Z - SMALL_A + INDEX_TO_SIZE_OFFSET];

        for (char c = SMALL_A; c <= SMALL_Z; ++c) {
            double count = charCounts.getOrDefault(c, DEFAULT_COUNT);
            if (count > ZERO) {
                textFrequencies[indexFor(c)] = count / text.length();
            }
        }
        return textFrequencies;
    }

    /**
     * Counts the number of occurrences of each character in text.
     *
     * @param text counting the occurrences of characters in this text
     * @return a mapping between characters and the number of times they appear in text
     */
    private HashMap<Character, Integer> getCharCounts(String text) {
        HashMap<Character, Integer> charCounts = new HashMap<>();

        for (Character c : text.toCharArray()) {
            charCounts.put(c, charCounts.getOrDefault(c, DEFAULT_COUNT) + COUNT_INCREMENT);
        }
        return charCounts;
    }

    /**
     * Given an array of frequencies of occurrences of letters in a text, computes the variance between these
     * frequencies to a constant "global" frequency saved in _frequencyTable.
     *
     * @param currentFrequencies an array of frequencies of letters in some text.
     * @return the variance between {@code currentFrequencies} to _frequencyTable.
     */
    private double computeVariance(double[] currentFrequencies) {
        double[] diffsSquared = new double[currentFrequencies.length];

        for (char c = SMALL_A; c <= SMALL_Z; ++c) {
            diffsSquared[indexFor(c)] = Math.pow(_frequencyTable[indexFor(c)] - currentFrequencies[indexFor(c)],
                    EXPONENT_2);
        }

        return sum(diffsSquared);
    }

    /**
     * Computes the sum of values in a double array.
     *
     * @param arr the array to sum up.
     * @return the sum of values in {@code arr}.
     */
    private double sum(double[] arr) {
        double sum = 0;

        for (double d : arr) {
            sum += d;
        }

        return sum;
    }

    /**
     * Returns the index of an English letter in a frequencies array.
     * If the given char is not an English letter the behaviour is undefined.
     *
     * @param c the English letter to get the index of.
     * @return c's index in a frequencies array.
     */
    private int indexFor(char c) {
        return c - SMALL_A;
    }
}
