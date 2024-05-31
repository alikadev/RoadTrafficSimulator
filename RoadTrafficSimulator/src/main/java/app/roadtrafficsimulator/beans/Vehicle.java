package app.roadtrafficsimulator.beans;

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
}
