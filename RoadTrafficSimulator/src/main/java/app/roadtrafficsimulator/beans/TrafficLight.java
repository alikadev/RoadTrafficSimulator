package app.roadtrafficsimulator.beans;

/**
 * Represents a traffic light with green and yellow durations.
 * Provides methods to get and set these durations.
 *
 * @author Elvin Kuci
 */
public class TrafficLight {
    /**
     * The duration of the green light.
     */
    double green;

    /**
     * The duration of the yellow light.
     */
    double yellow;

    /**
     * The next traffic light in the sequence.
     */
    TrafficLight next;

    /**
     * Constructs a TrafficLight with the specified durations and next traffic light.
     *
     * @param green the duration of the green light
     * @param yellow the duration of the yellow light
     * @param next the next traffic light in the sequence
     */
    public TrafficLight(double green, double yellow, TrafficLight next) {
        this.green = green;
        this.yellow = yellow;
        this.next = next;
    }

    /**
     * Returns the duration of the green light.
     *
     * @return the duration of the green light
     */
    public double getGreen() {
        return green;
    }

    /**
     * Sets the duration of the green light.
     *
     * @param green the new duration of the green light
     */
    public void setGreen(double green) {
        this.green = green;
    }

    /**
     * Returns the duration of the yellow light.
     *
     * @return the duration of the yellow light
     */
    public double getYellow() {
        return yellow;
    }

    /**
     * Sets the duration of the yellow light.
     *
     * @param yellow the new duration of the yellow light
     */
    public void setYellow(double yellow) {
        this.yellow = yellow;
    }

    /**
     * Returns the next traffic light in the sequence.
     *
     * @return the next traffic light in the sequence
     */
    public TrafficLight getNext() {
        return next;
    }

    /**
     * Sets the next traffic light in the sequence.
     *
     * @param next the next traffic light in the sequence
     */
    public void setNext(TrafficLight next) {
        this.next = next;
    }
}
