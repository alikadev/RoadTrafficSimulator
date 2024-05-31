package app.roadtrafficsimulator.beans;

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
}
