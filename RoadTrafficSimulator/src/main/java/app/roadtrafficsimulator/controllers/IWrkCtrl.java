package app.roadtrafficsimulator.controllers;

import app.roadtrafficsimulator.beans.Circuit;
import app.roadtrafficsimulator.beans.Vehicle;

public interface IWrkCtrl {
    /**
     * Render a vehicle on the screen.
     */
    void addVehicle(Vehicle v);

    /**
     * Destroy a vehicles on the screen.
     */
    void removeVehicle(Vehicle v);

    double getSpeedFactor();
}
