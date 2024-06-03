package app.roadtrafficsimulator.beans;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

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
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
    }

    /**
     * Create a vector the same value for X and Y.
     * @param v The value for X and Y.
     */
    public Vec2(double v) {
        x = new SimpleDoubleProperty(v);
        y = new SimpleDoubleProperty(v);
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
                "x=" + x.getValue() +
                ", y=" + y.getValue() +
                '}';
    }

    /**
     * Add 2 vectors. (a, b) + (x, y) = (a + x, b + y)
     * @param a The other vector.
     * @return The resulting vector.
     */
    public Vec2 add(Vec2 a) {
        return new Vec2(x.getValue() + a.x.getValue(), y.getValue() + a.y.getValue());
    }

    /**
     * Add a value. (a, b) + x = (a + x, b + x)
     * @param f The other vector.
     * @return The resulting vector.
     */
    public Vec2 add(double a) {
        return new Vec2(x.getValue() + a, y.getValue() + a);
    }

    /**
     * Subtract 2 vectors. (a, b) - (x, y) = (a - x, b - y)
     * @param a The other vector.
     * @return The resulting vector.
     */
    public Vec2 sub(Vec2 a) {
        return new Vec2(x.getValue() - a.x.getValue(), y.getValue() - a.y.getValue());
    }

    /**
     * Subtract 2 vectors. (a, b) - (x, y) = (a - x, b - y)
     * @param a The other vector.
     * @return The resulting vector.
     */
    public Vec2 sub(double a) {
        return new Vec2(x.getValue() - a, y.getValue() - a);
    }

    /**
     * Multiply by a factor.  (a, b) * x = (ax, bx)
     * @param f The factor.
     * @return The resulting vector.
     */
    public Vec2 mul(double f) {
        return new Vec2(x.getValue() * f, y.getValue() * f);
    }

    /**
     * Get the length of the vector.
     * @return The length of the vector.
     */
    public double length() {
        return Math.sqrt(x.getValue()*x.getValue() + y.getValue()*y.getValue());
    }

    /**
     * Get the normalized vector.
     * @return The length of the vector.
     */
    public Vec2 normal() {
        double length = length();
        return new Vec2(x.getValue()/length, y.getValue()/length);
    }

    /**
     * Get the X coordinate.
     * @return The X coordinate.
     */
    public double getX() {
        return x.getValue();
    }

    /**
     * Get the X coordinate property.
     * @return The X property.
     */
    public DoubleProperty getXProperty() {
        return x;
    }

    /**
     * Set the X coordinate.
     * @param x The X coordinate.
     */
    public void setX(double x) {
        this.x.setValue(x);
    }

    /**
     * Get the Y coordinate.
     * @return The Y coordinate.
     */
    public double getY() {
        return y.getValue();
    }

    /**
     * Get the Y coordinate property.
     * @return The Y property.
     */
    public DoubleProperty getYProperty() {
        return y;
    }

    /**
     * Set the Y coordinate.
     * @param y The Y coordinate.
     */
    public void setY(double y) {
        this.y.setValue(y);
    }

    /**
     * The X coordinate of the vector.
     */
    private SimpleDoubleProperty x;
    /**
     * The Y coordinate of the vector.
     */
    private SimpleDoubleProperty y;
}
