package il.co.hac.edu.yekhezkelyo.ex1;

/**
 * This interface defines a registrar, which is an object that is responsible for registering all of the required
 * implementations in a factory for a given build (based on the Command design pattern).
 * To replace an implementation one needs to only change the main function, which creates the registrar and sends it
 * to the {@code run()} method of {@link Controller}.
 */
public interface Initializer {
    /**
     * All registration code goes here.
     */
    void init();
}
