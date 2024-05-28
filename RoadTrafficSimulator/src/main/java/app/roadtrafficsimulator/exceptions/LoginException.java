package app.roadtrafficsimulator.exceptions;

public class LoginException extends Exception {
    public LoginException(String message) {
        super("Log In Exception: " + message);
    }
}
