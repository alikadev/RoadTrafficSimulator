package app.roadtrafficsimulator.beans;

import app.roadtrafficsimulator.helper.Physics;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

/**
 * Represents the actions that a road should be able to do.
 *
 * @autor Elvin Kuci
 */
public interface Roadable {
    /**
     * Returns the directional traffic map of the road.
     *
     * @return the directional traffic map of the road
     */
    Map<Direction, Pair<TrafficLight, List<Roadable>>> getDirectionalTrafficMap();

    /**
     * Returns the properties of the road.
     *
     * @return the properties of the road
     */
    List<InputField> getProperties();

    /**
     * Returns the resulting drawable element
     *
     * @return The end position
     */
    Node draw();

    /**
     * Return the road starting position
     *
     * @return The road starting vector / coordinates
     */
    Vec2 getStartPosition();

    /**
     * Set the traffic from code
     *
     * @param traffic The traffic in vehicle per minutes.
     */
    void setTraffic(double traffic);

    /**
     * Get the car rotation. This calculation is done with the whole vehicle
     * in argument to increase animation details one day...
     *
     * @param c The car
     * @return The angle in degrees
     */
    double getCarRotation(Vehicle c);

    /**
     * Standard size of a road in Switzerland
     * See the <a href="https://ge.ch/grandconseil/data/texte/PL12730A.pdf">source</a> here
     */
    static double WIDTH = (6.4 * Physics.METER) / 2;

    /**
     * Get the road speed limit.
     *
     * @return The speed limit in (m/s)
     */
    double getSpeedLimit();

    /**
     * Move the vehicle on the road. Might change the vehicle's road or request
     * to be destroyed (end of road).
     *
     * @param v The vehicle to move
     * @param distance The distance to move
     *
     * @return False if the vehicle finished its course
     */
    boolean moveVehicle(Vehicle v, double distance);

    /**
     * Update the internal timer with the DT and return true when the spawn timer finished.
     *
     * @param dt The delta time (time since the last update).
     *
     * @return True when a new vehicle should spawn.
     */
    boolean shouldSpawnVehicle(double dt);

    /**
     * Get the map of the road settings and values.
     *
     * @return The setting id to value map.
     */
    Map<String, Double> getSettings();

    /**
     * Set the settings from a map of setting id to value.
     *
     * @param settings The map of setting id to value.
     */
    void setSettings(Map<String, Double> settings);
}
