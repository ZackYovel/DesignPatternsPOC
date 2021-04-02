package il.co.hac.edu.yekhezkelyo.ex1;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * This class's sole responsibility is to execute the {@code run} method of {@link Controller} and give it
 * everything it needs. This way, if a UrlCheck is replaced or added, there is no need to modify {@link Controller},
 * just this class. This is not being done in {@link Main} in order to keep {@link Main} clean.
 */
public class App implements Registrar<UrlCheck> {

    /**
     * Run the application
     */
    public void run(){
        init();
        Controller controller = new Controller();
        controller.run();
    }

    /**
     * Run any initializations before running the controller.
     */
    private void init() {
        registerAll(UrlCheck.class);
    }
}
