package app.roadtrafficsimulator.beans;

import javafx.scene.Node;

import java.util.List;

/**
 * Describe a vehicle and it's capacities.
 *
 * @author Elvin Kuci
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

    /**
     * Set the road the vehicle is currently on.
     *
     * @param road The road.
     */
    void setRoad(Roadable road);

    /**
     * Get the road the vehicle is on.
     *
     * @return The road.
     */
    Roadable getRoad();

    /**
     * Get the position of the vehicle.
     *
     * @return The position vector.
     */
    Vec2 getPosition();

    /**
     * Move the vehicle by an offset vector.
     *
     * @param offset The offset vector.
     */
    void move(Vec2 offset);

    /**
     * Get the reaction time of the vehicle.
     *
     * @return The reaction time (second).
     */
    double getReactionTime();

    /**
     * Get the standard deceleration (not fully breaking).
     *
     * @return The deceleration (m/s^2).
     */
    double getDeceleration();

    /**
     * Get the breaking deceleration.
     *
     * @return The breaking deceleration (m/s^2)
     */
    double getBreakingSpeed();

    /**
     * Get the car acceleration.
     *
     * @return The acceleration (m/s^2)
     */
    double getAcceleration();

    /**
     * Get the security time.
     *
     * @return The security time (second).
     */
    double getSecurityTime();

    /**
     * Set the vehicle current speed.
     *
     * @param speed The vehicle speed (m/s).
     */
    void setSpeed(double speed);

    /**
     * Get the vehicle current speed.
     *
     * @return The vehicle speed (m/s).
     */
    double getSpeed();
}
