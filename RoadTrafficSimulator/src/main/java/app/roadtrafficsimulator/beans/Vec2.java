package app.roadtrafficsimulator.beans;

/**
 * Represents a vector in the 2D space.
 */
public class Vec2 {
    /**
     * Create a vector.
     * @param x The X coordinate.
     * @param y The Y coordinate.
     */
    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a vector the same value for X and Y.
     * @param v The value for X and Y.
     */
    public Vec2(double v) {
        this.x = v;
        this.y = v;
    }

    /**
     * Add 2 vectors.
     * @param a The other vector.
     * @return The resulting vector.
     */
    public Vec2 add(Vec2 a) {
        return new Vec2(a.x + x, a.y + y);
    }

    /**
     * Subtract 2 vectors.
     * @param a The other vector.
     * @return The resulting vector.
     */
    public Vec2 sub(Vec2 a) {
        return new Vec2(a.x - x, a.y - y);
    }

    /**
     * Get the X coordinate.
     * @return The X coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Set the X coordinate.
     * @param x The X coordinate.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Get the Y coordinate.
     * @return The Y coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Set the Y coordinate.
     * @param y The Y coordinate.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * The X coordinate of the vector.
     */
    private double x;
    /**
     * The Y coordinate of the vector.
     */
    private double y;
}
