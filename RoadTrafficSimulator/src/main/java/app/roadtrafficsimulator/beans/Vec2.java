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
     * Copy constructor.
     * @param v The value for X and Y.
     */
    public Vec2(Vec2 v) {
        this.x = v.x;
        this.y = v.y;
    }

    @Override
    public String toString() {
        return "Vec2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * Add 2 vectors. (a, b) + (x, y) = (a + x, b + y)
     * @param a The other vector.
     * @return The resulting vector.
     */
    public Vec2 add(Vec2 a) {
        return new Vec2(x + a.x, y + a.y);
    }

    /**
     * Add a value. (a, b) + x = (a + x, b + x)
     * @param f The other vector.
     * @return The resulting vector.
     */
    public Vec2 add(double a) {
        return new Vec2(x + a, y + a);
    }

    /**
     * Subtract 2 vectors. (a, b) - (x, y) = (a - x, b - y)
     * @param a The other vector.
     * @return The resulting vector.
     */
    public Vec2 sub(Vec2 a) {
        return new Vec2(x - a.x, y - a.y);
    }

    /**
     * Subtract 2 vectors. (a, b) - (x, y) = (a - x, b - y)
     * @param a The other vector.
     * @return The resulting vector.
     */
    public Vec2 sub(double a) {
        return new Vec2(x - a, y - a);
    }

    /**
     * Multiply by a factor.  (a, b) * x = (ax, bx)
     * @param f The factor.
     * @return The resulting vector.
     */
    public Vec2 mul(double f) {
        return new Vec2(x * f, y * f);
    }

    /**
     * Get the length of the vector.
     * @return The length of the vector.
     */
    public double length() {
        return Math.sqrt(x*x + y*y);
    }

    /**
     * Get the normalized vector.
     * @return The length of the vector.
     */
    public Vec2 normal() {
        double length = length();
        return new Vec2(x/length, y/length);
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
