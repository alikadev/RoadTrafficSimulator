package app.roadtrafficsimulator.exceptions;

/**
 * This is an exception cause by the login state.
 *
 * @author Elvin Kuci
 */
public class LoginException extends Exception {
    /**
     * Create the exception by message.
     *
     * @param message The message.
     */
    public LoginException(String message) {
        super("Log In Exception: " + message);
    }
}
