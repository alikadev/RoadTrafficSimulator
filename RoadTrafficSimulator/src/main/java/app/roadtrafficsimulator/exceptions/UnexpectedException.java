package app.roadtrafficsimulator.exceptions;

/**
 * Represent an unexpected exception to handle exceptions that shouldn't be a problem. It should be used like an
 * "Unreachable" comment or a C-assert or something like that...
 *
 * @author Elvin Kuci
 */
public class UnexpectedException extends RuntimeException {
    /**
     * Create the exception
     *
     * @param message The exception message
     */
    public UnexpectedException(String message) {
        super("UnexpectedException: " + message);
    }
}
