package app.roadtrafficsimulator.helper;

import java.util.function.Consumer;

/**
 * This class only contains static methods and is used to make JavaFX easier.
 *
 * @author Elvin Kuci
 */
public class FX {
    /**
     * Process the callback to the element type. Should be mainly used to set attributes to JavaFX structure.
     * @param node The element
     * @param fn The process that you'll need to do
     * @return The node
     * @param <T> The type of the function
     */
    public static <T> T set(T node, Consumer<T> fn) {
        fn.accept(node);
        return node;
    }
}
