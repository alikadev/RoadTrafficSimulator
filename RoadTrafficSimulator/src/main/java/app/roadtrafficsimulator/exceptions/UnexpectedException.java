package app.roadtrafficsimulator.exceptions;

/**
 * Represent an unexcpected exception to handle exceptions that shouldn't be a problem. It should be used like an
 * "Unreachable" comment or something like that...
 */
public class UnexpectedException extends RuntimeException {
    /**
     * Create the exception
     * @param message The exception message
     */
    public UnexpectedException(String message) {
        super("UnexceptedException: " + message);
    }
}
