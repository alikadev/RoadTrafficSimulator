package app.roadtrafficsimulator.exceptions;

/**
 * This is a runtime exception used to specify that some functionality is not implemented yet while developing.
 * This is a RuntimeException, so it doesn't need to be caught and doesn't need the "throws" specifier.
 *
 * @author Elvin Kuci
 */
public class NotImplementedYet extends RuntimeException {
    /**
     * Create the not implemented yet exception with a default message.
     */
    public NotImplementedYet() {
        super("Not implemented yet");
    }

    /**
     * Create the not implemented yet exception with a comment. The `comment` should be the name of the thing that is
     * not implemented to make more sense of the message ("comment is not implemented yet").
     *
     * @param comment The so-called comment.
     */
    public NotImplementedYet(String comment) {
        super(comment + " is implemented yet");
    }
}
