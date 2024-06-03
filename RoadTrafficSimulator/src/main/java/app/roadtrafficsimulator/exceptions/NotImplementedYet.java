package app.roadtrafficsimulator.exceptions;

public class NotImplementedYet extends RuntimeException {
    public NotImplementedYet() {
        super("Not implemented yet");
    }
    public NotImplementedYet(String comment) {
        super(comment + " is implemented yet");
    }
}
