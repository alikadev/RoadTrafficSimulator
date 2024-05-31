package app.roadtrafficsimulator.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a car with various physical properties and implements the Vehicle interface.
 * Provides methods to get and set these properties.
 *
 * @autor Elvin Kuci
 */
public class Car implements Vehicle {

    /**
     * Constructs a Car with the specified physical properties.
     *
     * @param reactionTime the reaction time of the car (in seconds)
     * @param decelerationSpeed the deceleration speed of the car (in meters per second squared)
     * @param breakingSpeed the emergency braking speed of the car (in meters per second squared)
     * @param acceleration the acceleration of the car (in meters per second squared)
     * @param securityTime the security time of the car (in seconds)
     */
    public Car(double reactionTime, double decelerationSpeed, double breakingSpeed, double acceleration, double securityTime) {
        this.reactionTime = new InputField("Temps de réaction (s)", reactionTime);
        this.decelerationSpeed = new InputField("Décélération (m/s^2)", decelerationSpeed);
        this.breakingSpeed = new InputField("Décélération d'urgence (m/s^2)", breakingSpeed);
        this.acceleration = new InputField("Accélération (m/s^2)", acceleration);
        this.securityTime = new InputField("Temps de sécurité (s)", securityTime);

        props = new ArrayList<>();
        props.add(this.reactionTime);
        props.add(this.decelerationSpeed);
        props.add(this.breakingSpeed);
        props.add(this.acceleration);
        props.add(this.securityTime);
    }

    /**
     * Returns the road the car is on.
     *
     * @return the road the car is on
     */
    public Roadable getRoad() {
        return road;
    }

    /**
     * Sets the road the car is on.
     *
     * @param road the new road the car is on
     */
    public void setRoad(Roadable road) {
        this.road = road;
    }

    /**
     * Returns the position of the car.
     *
     * @return the position of the car
     */
    public Vec2 getPosition() {
        return position;
    }

    /**
     * Sets the position of the car.
     *
     * @param position the new position of the car
     */
    public void setPosition(Vec2 position) {
        this.position = position;
    }

    /**
     * Returns the reaction time of the car.
     *
     * @return the reaction time of the car
     */
    public InputField getReactionTime() {
        return reactionTime;
    }

    /**
     * Returns the deceleration speed of the car.
     *
     * @return the deceleration speed of the car
     */
    public InputField getDecelerationSpeed() {
        return decelerationSpeed;
    }

    /**
     * Returns the emergency braking speed of the car.
     *
     * @return the emergency braking speed of the car
     */
    public InputField getBreakingSpeed() {
        return breakingSpeed;
    }

    /**
     * Returns the acceleration of the car.
     *
     * @return the acceleration of the car
     */
    public InputField getAcceleration() {
        return acceleration;
    }

    /**
     * Returns the security time of the car.
     *
     * @return the security time of the car
     */
    public InputField getSecurityTime() {
        return securityTime;
    }

    @Override
    public List<InputField> getProperties() {
        return props;
    }

    /**
     * The road the car is on.
     */
    private Roadable road;

    /**
     * The position of the car.
     */
    private Vec2 position;

    /**
     * The reaction time of the car.
     */
    private InputField reactionTime;

    /**
     * The deceleration speed of the car.
     */
    private InputField decelerationSpeed;

    /**
     * The emergency braking speed of the car.
     */
    private InputField breakingSpeed;

    /**
     * The acceleration of the car.
     */
    private InputField acceleration;

    /**
     * The security time of the car.
     */
    private InputField securityTime;

    /**
     * The list of all properties of the car.
     */
    private List<InputField> props;
}
