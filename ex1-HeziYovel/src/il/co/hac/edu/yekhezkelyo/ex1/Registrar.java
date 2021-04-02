package il.co.hac.edu.yekhezkelyo.ex1;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * This interface defines (and implements) a type that performs auto-registration of classes at factories.
 *
 * Usage:
 * Given a super type {@code s} that needs to be registered at a factory {@code f}, every implementing class {@code c}
 * of {@code s} must have a {@code public static void register()} function, which performs the actual registration
 * of {@code c} at {@code f}.
 * In addition, somewhere in the execution flow of the application, a {@code Registrar<s> r} has to be instantiated,
 * and {@code r.registerAll(Class<s> clazz)} must be invoked.
 *
 * There is no need for the {@code Registrar<T>} implementor to know anything about the factories, since the actual
 * registration is being performed by the registered classes.
 *
 * @param <T> a super type that needs to be registered in a factory.
 */
public interface Registrar<T> {

    /**
     * The name of the function that will be invoked to perform the actual registration.
     */
    String REGISTER_FUNC_NAME = "register";
    /**
     * An error message to show if a sub-class does not have the "register" function.
     */
    String ERROR_MESSAGE = " must have a public static void register() function";

    /**
     * Trigger the register functions of all sub-classes of clazz.
     *
     * @param clazz a super-type that needs to be registered.
     */
    default void registerAll(Class<T> clazz){
        Reflections reflections = new Reflections(getClass().getPackageName());
        Set<Class<? extends T>> impls = reflections.getSubTypesOf(clazz);

        for (Class<? extends T> check : impls) {
            try {
                Method register = check.getDeclaredMethod(REGISTER_FUNC_NAME);
                register.invoke(check);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
                System.err.println(check.getSimpleName() + ERROR_MESSAGE);
            }
        }
    }
}
