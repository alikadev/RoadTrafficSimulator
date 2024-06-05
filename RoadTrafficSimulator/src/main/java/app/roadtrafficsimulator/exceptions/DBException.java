package app.roadtrafficsimulator.exceptions;

/**
 * This represents an exception from the database.
 *
 * @author Elvin Kuci
 */
public class DBException extends Exception {
    /**
     * Create the exception by message.
     *
     * @param message The message.
     */
    public DBException(String message) {
        super("Database Exception: " + message);
    }
}
