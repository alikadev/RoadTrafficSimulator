package app.roadtrafficsimulator.workers;

import app.roadtrafficsimulator.beans.Vehicle;
import javafx.scene.image.ImageView;

/**
 * This interface is implemented by the worker and represents the actions that the SimulationWorker will ask the
 * Worker to do.
 *
 * @author Elvin Kuci
 */
public interface ISimulationWrk {
    /**
     * Add a vehicle on the circuit.
     *
     * @param v The vehicle
     */
    void addVehicle(Vehicle v);

    /**
     * Remove a vehicle from the circuit.
     *
     * @param v The vehicle
     */
    void removeVehicle(Vehicle v);

    /**
     * Get a car texture
     *
     * @return The texture
     */
    ImageView getCarTexture();
}
