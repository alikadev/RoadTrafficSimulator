package app.roadtrafficsimulator.exceptions;

public class DBException extends Exception {
    public DBException(String message) {
        super("Database Exception: " + message);
    }
}
