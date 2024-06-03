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

    void setRoad(Roadable road);
    Roadable getRoad();
    Vec2 getPosition();
    void setPosition(Vec2 position);
    double getReactionTime();
    double getDeceleration();
    double getBreakingSpeed();
    double getAcceleration();
    double getSecurityTime();
    void setSpeed(double speed);
    double getSpeed();
}
