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
    //Map<Direction, List<Pair<TrafficLight, Roadable>>> getDirectionalTrafficMap();

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
     * Get the car rotation. This calculation is done with the whole vehicle
     * in argument to increase animation details one day...
     *
     * @param c The car
     * @return The angle in degrees
     */
    double getCarRotation(Vehicle c);

    /**
     * Standard size of a road in Switzerland
     *
     * See the <a href="https://ge.ch/grandconseil/data/texte/PL12730A.pdf">source</a> here
     */
    static double WIDTH = (6.4 * Physics.METER) / 2;
}
