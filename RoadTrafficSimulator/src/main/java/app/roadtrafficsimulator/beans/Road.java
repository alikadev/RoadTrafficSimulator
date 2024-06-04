package app.roadtrafficsimulator.beans;

import app.roadtrafficsimulator.exceptions.UnexpectedException;
import app.roadtrafficsimulator.helper.FX;
import app.roadtrafficsimulator.helper.Physics;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a road that can create new vehicles.
 * Provides methods to get and set its properties.
 *
 * @author Elvin Kuci
 */
public class Road implements Roadable {
    /**
     * Constructs a Road with the specified head position, direction, size, and traffic density.
     *
     * @param id The id of the road
     * @param iv The ImageView that will be used to draw the road
     * @param headPosition the head position of the road
     * @param direction the direction of the road traffic
     * @param size the size of the road (in meters)
     * @param speedLimit the speed limit of the road (in kilometers per hour)
     */
    public Road(String id, ImageView iv, Vec2 headPosition, Direction direction, double size, double speedLimit) {
        this.speedLimit = new InputField("Vitesse (km/h)", speedLimit, "+-", 0);
        this.size = new InputField("Size (m)", size);
        this.traffic = new InputField("Densitée (véhicule/m)", 0);
        this.headPosition = headPosition;
        this.direction = direction;

        props = new ArrayList<>();
        props.add(this.speedLimit);
        props.add(this.size);
        props.add(this.traffic);

        timer = 0;
        this.id = id;

        switch (direction) {
            case RIGHT:
            case LEFT:
                iv.setRotate(90);
        }

        this.texture = iv.snapshot(FX.set(new SnapshotParameters(), p -> p.setFill(Color.TRANSPARENT)), null);
    }

    /**
     * Draw the texture on the canvas to fill it
     */
    private void drawCanvasContent() {
        // Get context
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());

        double iw = texture.getWidth();
        double ih = texture.getHeight();

        // Draw road texture repeatedly
        for (double x = 0; x < canvas.getWidth(); x += iw) {
            for (double y = 0; y < canvas.getHeight(); y += ih) {
                gc.drawImage(texture, x, y);
            }
        }

        // Draw outer stroke
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0,0,canvas.getWidth()-1, canvas.getHeight()-1);
    }

    @Override
    public Map<Direction, Pair<TrafficLight, List<Roadable>>> getDirectionalTrafficMap() {
        return null;
    }

    @Override
    public List<InputField> getProperties() {
        return props;
    }

    @Override
    public Node draw() {
        // Scale between the road and the texture
        double scale = texture.getWidth() / Roadable.WIDTH;

        // Calculate position and size
        Vec2 halfRoadWidth = new Vec2(Roadable.WIDTH/2);
        Vec2 pos = getStartPosition().sub(halfRoadWidth);
        // Upscale the size to be able to draw all the pixels
        Vec2 size = getEndPosition().sub(getStartPosition()).add(new Vec2(Roadable.WIDTH)).mul(scale);

        // Create the canvas
        canvas = new Canvas();
        canvas.setWidth(size.getX());
        canvas.setHeight(size.getY());
        canvas.setLayoutX(pos.getX());
        canvas.setLayoutY(pos.getY());
        drawCanvasContent();
        // Downscale the texture to its original value
        canvas.getTransforms().add(new Scale(1.0/scale,1.0/scale));
        // Disable parent management to not cause UI problems when overflowing
        canvas.setManaged(false);

        return canvas;
    }

    @Override
    public double getCarRotation(Vehicle c) {
        switch (direction) {
            case TOP: return 0;
            case DOWN: return 180;
            case RIGHT: return 90;
            case LEFT: return 270;
        }

        throw new UnexpectedException("Not all direction are managed up here!");
    }

    @Override
    public boolean moveVehicle(Vehicle v, double distance) {
        Vec2 end = getEndPosition();
        Vec2 endVector = end.sub(v.getPosition());
        double endDistance = endVector.length();

        // Check if we finish this road "tile"
        if (endDistance < distance) {
            // Check if road is finished
            if (next == null) {
                return false;
            }

            // Switch to next road and continue updating...
            v.setRoad(next);
            return next.moveVehicle(v, distance - endDistance);
        }

        // Calculate new position
        v.move(endVector.normal().mul(distance));
        return true;
    }

    @Override
    public boolean shouldSpawnVehicle(double dt) {
        // Vehicle per minutes (v/m)
        double vpm = traffic.getValue();
        if (vpm == 0) return false;
        // Vehicle per seconds (v/s)
        double vps = vpm / 60;
        // Second per vehicle (s/v)
        double spv = 1 / vps;

        timer += dt;
        if (timer >= spv) {
            timer -= spv;
            return true;
        }

        return false;
    }

    @Override
    public Map<String, Double> getSettings() {
        HashMap<String, Double> map = new HashMap<>();
        map.put(id + "-speedLimit", speedLimit.getValue());
        map.put(id + "-size", size.getValue());
        map.put(id + "-traffic", getTraffic().getValue());
        return map;
    }

    @Override
    public void setSettings(Map<String, Double> settings) {
        for (String key : settings.keySet()) {
            if (key.contains(id)) {
                String subKey = key.substring(id.length());
                switch (subKey) {
                    case "-speedLimit":
                        speedLimit.valueProperty().setValue(settings.get(key).toString());
                        break;
                    case "-size":
                        size.valueProperty().setValue(settings.get(key).toString());
                        break;
                    case "-traffic":
                        traffic.valueProperty().setValue(settings.get(key).toString());
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * Get the next road.
     * @return The next road.
     */
    public Roadable getNext() {
        return next;
    }

    /**
     * Set the next road.
     * @param next The next road.
     */
    public void setNext(Roadable next) {
        this.next = next;
    }

    /**
     * Returns the speed limit input field of the road.
     *
     * @return the speed limit in meter per second (m/s)
     */
    public double getSpeedLimit() {
        return speedLimit.getValue() * Physics.KM_H;
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
    @Override
    public Vec2 getStartPosition() {
        return headPosition;
    }

    /**
     * Returns the head position of the road.
     *
     * @return the head position of the road
     */
    public Vec2 getEndPosition() {
        switch (direction) {
            case TOP: return new Vec2(headPosition.getX(), headPosition.getY() - size.getValue());
            case DOWN: return new Vec2(headPosition.getX(), headPosition.getY() + size.getValue());
            case LEFT: return new Vec2(headPosition.getX() - size.getValue(), headPosition.getY());
            case RIGHT: return new Vec2(headPosition.getX() + size.getValue(), headPosition.getY());
        }
        throw new UnexpectedException("Unexpected direction value");
    }

    @Override
    public void setTraffic(double traffic) {
        this.traffic.valueProperty().setValue(Double.toString(traffic));
    }

    /**
     * The ID of the road
     */
    private final String id;

    /**
     * The rendered rectangle
     */
    private Canvas canvas;

    /**
     * The image to render on the road.
     * It should be already transformed (rotated, ...) before the `draw` call.
     */
    private final Image texture;

    /**
     * The speed limit input field of the road. (Value is in km/h)
     */
    private final InputField speedLimit;

    /**
     * The size input field of the road.
     */
    private final InputField size;

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
    private final Vec2 headPosition;

    /**
     * The properties of the road.
     */
    private List<InputField> props;

    /**
     * The next road.
     */
    private Roadable next;

    /**
     * This is a timer/counter.
     */
    private double timer;
}
