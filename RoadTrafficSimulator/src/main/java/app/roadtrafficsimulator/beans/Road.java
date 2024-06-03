package app.roadtrafficsimulator.beans;

import app.roadtrafficsimulator.exceptions.UnexpectedException;
import app.roadtrafficsimulator.helper.FX;
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
     * Constructs a Road with the specified head position, direction, size, and traffic density.
     *
     * @param iv The ImageView that will be used to draw the road
     * @param headPosition the head position of the road
     * @param direction the direction of the road traffic
     * @param size the size of the road (in meters)
     * @param speedLimit the speed limit of the road (in kilometers per hour)
     */
    public Road(ImageView iv, Vec2 headPosition, Direction direction, double size, double speedLimit) {
        this.speedLimit = new InputField("Vitesse (km/h)", speedLimit, "+-", 0);
        this.size = new InputField("Size (m)", size);
        this.traffic = new InputField("Densitée (véhicule/m)", 0);
        this.headPosition = headPosition;
        this.direction = direction;

        props = new ArrayList<>();
        props.add(this.speedLimit);
        props.add(this.size);
        props.add(this.traffic);

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
    private void drawRepeatedImage() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());

        double iw = texture.getWidth();
        double ih = texture.getHeight();

        for (double x = 0; x < canvas.getWidth(); x += iw) {
            for (double y = 0; y < canvas.getHeight(); y += ih) {
                gc.drawImage(texture, x, y);
            }
        }
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

        // Calculate positon and size
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
        drawRepeatedImage();
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

    /**
     * The rendered rectangle
     */
    Canvas canvas;

    /**
     * The image to render on the road.
     * It should be already transformed (rotated, ...) before the `draw` call.
     */
    Image texture;

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
    private Vec2 headPosition;

    /**
     * The properties of the road.
     */
    private List<InputField> props;
    /**
     * The next road.
     */
    private Roadable next;
}
