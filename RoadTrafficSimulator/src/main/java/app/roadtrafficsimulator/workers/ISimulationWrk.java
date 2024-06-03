package app.roadtrafficsimulator.workers;

import app.roadtrafficsimulator.beans.Vehicle;

public interface ISimulationWrk {
    /**
     * Add a vehicle on the circuit.
     * @param v The vehicle
     */
    void addVehicle(Vehicle v);

    /**
     * Remove a vehicle from the circuit.
     * @param v The vehicle
     */
    void removeVehicle(Vehicle v);
}
