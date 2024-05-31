package app.roadtrafficsimulator.beans;

import app.roadtrafficsimulator.exceptions.UnexpectedException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a road that can create new vehicles.
 * Provides methods to get and set its properties.
 *
 * @autor Elvin Kuci
 */
public class Road implements Roadable {

    /**
     * Constructs a Road with the specified head position, direction, and size.
     *
     * @param backPosition the head position of the road
     * @param direction the direction of the road traffic
     * @param size the size of the road (in meters)
     */
    public Road(Vec2 backPosition, Direction direction, double size) {
        this.speedLimit = new InputField("Vitesse (km/h)", 0, "+-", 0);
        this.size = new InputField("Size (m)", size);
        this.traffic = new InputField("Densitée (véhicule/m)", 0);
        this.backPosition = backPosition;
        this.direction = direction;

        props = new ArrayList<>();
        props.add(this.speedLimit);
        props.add(this.size);
        props.add(this.traffic);
    }

    /**
     * Constructs a Road with the specified head position, direction, size, and traffic density.
     *
     * @param backPosition the head position of the road
     * @param direction the direction of the road traffic
     * @param size the size of the road (in meters)
     * @param speedLimit the speed limit of the road (in kilometers per hour)
     */
    public Road(Vec2 backPosition, Direction direction, double size, double speedLimit) {
        this.speedLimit = new InputField("Vitesse (km/h)", speedLimit, "+-", 0);
        this.size = new InputField("Size (m)", size);
        this.traffic = new InputField("Densitée (véhicule/m)", 0);
        this.backPosition = backPosition;
        this.direction = direction;

        props = new ArrayList<>();
        props.add(this.speedLimit);
        props.add(this.size);
        props.add(this.traffic);
    }

    /**
     * Constructs a Road with the specified head position, direction, size, traffic density, and speed limit.
     *
     * @param backPosition the head position of the road
     * @param direction the direction of the road traffic
     * @param size the size of the road (in meters)
     * @param traffic the traffic density of the road (vehicles per meter)
     * @param speedLimit the speed limit of the road (in kilometers per hour)
     */
    public Road(Vec2 backPosition, Direction direction, double size, double speedLimit, double traffic) {
        this.speedLimit = new InputField("Vitesse (km/h)", speedLimit, "+-", 0);
        this.size = new InputField("Size (m)", size);
        this.traffic = new InputField("Densitée (véhicule/m)", traffic);
        this.backPosition = backPosition;
        this.direction = direction;

        props = new ArrayList<>();
        props.add(this.speedLimit);
        props.add(this.size);
        props.add(this.traffic);
    }

    @Override
    public Map<Direction, Pair<TrafficLight, List<Roadable>>> getDirectionalTrafficMap() {
        return null;
    }

    @Override
    public List<InputField> getProperties() {
        return props;
    }

    public Roadable getNext() {
        return next;
    }

    public void setNext(Roadable next) {
        this.next = next;
    }

    /**
     * Returns the speed limit input field of the road.
     *
     * @return the speed limit input field of the road
     */
    public InputField getSpeedLimit() {
        return speedLimit;
    }

    /**
     * Returns the size input field of the road.
     *
     * @return the size input field of the road
     */
    public InputField getSize() {
        return size;
    }

    /**
     * Returns the traffic input field of the road.
     *
     * @return the traffic input field of the road
     */
    public InputField getTraffic() {
        return traffic;
    }

    /**
     * Returns the direction of the road.
     *
     * @return the direction of the road
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Returns the head position of the road.
     *
     * @return the head position of the road
     */
    public Vec2 getPosition() {
        return backPosition;
    }

    /**
     * Returns the head position of the road.
     *
     * @return the head position of the road
     */
    public Vec2 getEndPosition() {
        switch (direction) {
            case TOP: return new Vec2(backPosition.getX(), backPosition.getY() - size.getValue());
            case DOWN: return new Vec2(backPosition.getX(), backPosition.getY() + size.getValue());
            case LEFT: return new Vec2(backPosition.getX() - size.getValue(), backPosition.getY());
            case RIGHT: return new Vec2(backPosition.getX() + size.getValue(), backPosition.getY());
        }
        throw new UnexpectedException("Unexpected direction value");
    }

    /**
     * The speed limit input field of the road.
     */
    private InputField speedLimit;

    /**
     * The size input field of the road.
     */
    private InputField size;

    /**
     * The traffic input field of the road.
     */
    private InputField traffic;

    /**
     * The direction of the road.
     */
    private Direction direction;

    /**
     * The head position of the road.
     */
    private Vec2 backPosition;

    /**
     * The properties of the road.
     */
    private List<InputField> props;
    /**
     * The next road.
     */
    private Roadable next;
}
