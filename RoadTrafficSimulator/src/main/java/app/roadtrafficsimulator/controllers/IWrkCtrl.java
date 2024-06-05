package app.roadtrafficsimulator.controllers;

import app.roadtrafficsimulator.beans.Vehicle;

/**
 * This interface is used to describe the worker's needs from the controller.
 *
 * @author Kuci Elvin
 */
public interface IWrkCtrl {
    /**
     * Add / Render a vehicle on the screen.
     */
    void addVehicle(Vehicle v);

    /**
     * Remove / Destroy a vehicles on the screen.
     */
    void removeVehicle(Vehicle v);

    /**
     * Get the simulation's speed factor.
     *
     * @return The speed factor.
     */
    double getSpeedFactor();
}
