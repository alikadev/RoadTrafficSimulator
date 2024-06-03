package app.roadtrafficsimulator.beans;

import javafx.scene.Node;

import java.util.List;

/**
 * Describe a vehicle and it's capacities.
 */
public interface Vehicle {
    /**
     * Returns the list of all properties of the car.
     *
     * @return the list of all properties of the car
     */
    List<InputField> getProperties();

    /**
     * Returns the resulting drawable element
     *
     * @return The end position
     */
    Node draw();
}
