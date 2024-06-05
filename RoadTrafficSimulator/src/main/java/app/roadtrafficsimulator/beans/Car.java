package app.roadtrafficsimulator.beans;

import app.roadtrafficsimulator.exceptions.UnexpectedException;
import app.roadtrafficsimulator.helper.FX;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a car with various physical properties and implements the Vehicle interface.
 * Provides methods to get and set these properties.
 *
 * @author Elvin Kuci
 */
public class Car implements Vehicle {

    /**
     * Constructs a Car with the specified physical properties. Should only be used to
     * define the default car.
     * An instance of this car will be able to be drawn because the texture is not set.
     *
     * @param reactionTime the reaction time of the car (in seconds)
     * @param deceleration the deceleration speed of the car (in meters per second squared)
     * @param breakingSpeed the emergency braking speed of the car (in meters per second squared)
     * @param acceleration the acceleration of the car (in meters per second squared)
     * @param securityTime the security time of the car (in seconds)
     */
    public Car(double reactionTime, double deceleration, double breakingSpeed, double acceleration, double securityTime) {
        this.reactionTime = new InputField("Temps de réaction (s)", reactionTime);
        this.deceleration = new InputField("Décélération (m/s^2)", deceleration);
        this.breakingSpeed = new InputField("Décélération d'urgence (m/s^2)", breakingSpeed);
        this.acceleration = new InputField("Accélération (m/s^2)", acceleration);
        this.securityTime = new InputField("Temps de sécurité (s)", securityTime);

        props = new ArrayList<>();
        props.add(this.reactionTime);
        props.add(this.deceleration);
        props.add(this.breakingSpeed);
        props.add(this.acceleration);
        props.add(this.securityTime);

        speed = 0;
    }

    /**
     * Create a car from the default car.
     *
     * @param iv The ImageView that will be used to draw the car
     * @param car The car that will be deeply copied
     * @param rd The current road
     */
    public Car(ImageView iv, Car car, Roadable rd) {
        this.reactionTime = new InputField(car.reactionTime.getValueLabel(), car.reactionTime.getValue());
        this.deceleration = new InputField(car.deceleration.getValueLabel(), car.deceleration.getValue());
        this.breakingSpeed = new InputField(car.breakingSpeed.getValueLabel(), car.breakingSpeed.getValue());
        this.acceleration = new InputField(car.acceleration.getValueLabel(), car.acceleration.getValue());
        this.securityTime = new InputField(car.securityTime.getValueLabel(), car.securityTime.getValue());

        props = new ArrayList<>();
        props.add(this.reactionTime);
        props.add(this.deceleration);
        props.add(this.breakingSpeed);
        props.add(this.acceleration);
        props.add(this.securityTime);

        this.imageBase = iv;

        this.road = rd;
        position = new Vec2(road.getStartPosition());

        speed = this.road.getSpeedLimit();
    }

    @Override
    public Node draw() {
        if (road == null)
            throw new UnexpectedException("Miss use of the Car class. The car isn't on any road!");

        // Manage rotation
        imageBase.setRotate(road.getCarRotation(this) + 90);
        Image texture = imageBase.snapshot(FX.set(new SnapshotParameters(), p -> p.setFill(Color.TRANSPARENT)), null);

        // Render on canvas without downscale
        Canvas c = new Canvas(texture.getWidth(), texture.getHeight());
        c.getGraphicsContext2D().setImageSmoothing(false);
        c.getGraphicsContext2D().drawImage(texture, 0, 0);

        // Set position
        c.layoutXProperty().bind(position.getXProperty().subtract(Roadable.WIDTH/2));
        c.layoutYProperty().bind(position.getYProperty().subtract(Roadable.WIDTH/2));

        // Downscale the canvas
        double scale = texture.getWidth() / Roadable.WIDTH;
        c.getTransforms().add(new Scale(1.0/scale, 1.0/scale));

        // Disable parent management to not cause UI problems when overflowing
        c.setManaged(false);

        return c;
    }

    @Override
    public Roadable getRoad() {
        return road;
    }

    @Override
    public void setRoad(Roadable road) {
        this.road = road;
    }

    @Override
    public Vec2 getPosition() {
        return position;
    }

    @Override
    public void move(Vec2 offset) {
        position.setX(position.getX() + offset.getX());
        position.setY(position.getY() + offset.getY());
    }

    @Override
    public double getReactionTime() {
        return reactionTime.getValue();
    }

    @Override
    public double getDeceleration() {
        return deceleration.getValue();
    }

    @Override
    public double getBreakingSpeed() {
        return breakingSpeed.getValue();
    }

    @Override
    public double getAcceleration() {
        return acceleration.getValue();
    }

    @Override
    public double getSecurityTime() {
        return securityTime.getValue();
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public List<InputField> getProperties() {
        return props;
    }

    /**
     * The base ImageView used to render the texture.
     */
    private ImageView imageBase;
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
    private final InputField reactionTime;

    /**
     * The deceleration speed of the car.
     */
    private final InputField deceleration;

    /**
     * The emergency braking speed of the car.
     */
    private final InputField breakingSpeed;

    /**
     * The acceleration of the car.
     */
    private final InputField acceleration;

    /**
     * The security time of the car.
     */
    private final InputField securityTime;

    /**
     * The list of all properties of the car.
     */
    private final List<InputField> props;

    /**
     * The current speed of the car
     */
    private double speed;
}
