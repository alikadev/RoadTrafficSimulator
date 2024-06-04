package app.roadtrafficsimulator.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a circuit containing roads and vehicles.
 * Provides methods to get and set the default vehicle, roads, and vehicles.
 *
 * @autor Elvin Kuci
 */
public class Circuit {

    /**
     * Constructs a Circuit with a default vehicle and empty lists of roads and vehicles.
     */
    public Circuit() {
        defaultVehicle = new Car(0.2, -4.5, -7.3, 5.5, 2);
        roads = new ArrayList<>();
        vehicles = new ArrayList<>();
    }

    /**
     * Returns the default vehicle of the circuit.
     *
     * @return the default vehicle of the circuit
     */
    public Vehicle getDefaultVehicle() {
        return defaultVehicle;
    }

    /**
     * Sets the default vehicle of the circuit.
     *
     * @param defaultVehicle the new default vehicle of the circuit
     */
    public void setDefaultVehicle(Vehicle defaultVehicle) {
        this.defaultVehicle = defaultVehicle;
    }

    /**
     * Returns the list of roads in the circuit.
     *
     * @return the list of roads in the circuit
     */
    public List<Roadable> getRoads() {
        return roads;
    }

    /**
     * Sets the list of roads in the circuit.
     *
     * @param roads the new list of roads in the circuit
     */
    public void setRoads(List<Roadable> roads) {
        this.roads = roads;
    }

    /**
     * Returns the list of vehicles in the circuit.
     *
     * @return the list of vehicles in the circuit
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Sets the list of vehicles in the circuit.
     *
     * @param vehicles the new list of vehicles in the circuit
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * The default vehicle of the circuit.
     */
    private Vehicle defaultVehicle;

    /**
     * The list of roads in the circuit.
     */
    private List<Roadable> roads;

    /**
     * The list of vehicles in the circuit.
     */
    private List<Vehicle> vehicles;
}
