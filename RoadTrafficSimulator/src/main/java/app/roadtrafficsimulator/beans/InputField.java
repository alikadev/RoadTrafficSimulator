package app.roadtrafficsimulator.beans;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;

/**
 * Represents an input field with a value and an optional tolerance.
 * Provides methods to get and set these properties.
 *
 * @author Elvin Kuci
 */
public class InputField {

    /**
     * Constructs an InputField with the specified value label and value.
     *
     * @param valueLabel the label for the value
     * @param value the value of the input field
     */
    public InputField(String valueLabel, double value) {
        this.valueLabel = valueLabel;
        this.value = new SimpleStringProperty("");
        this.value.setValue(String.valueOf(value));

        toleranceLabel = null;
        tolerence = null;
    }

    /**
     * Constructs an InputField with the specified value label, value, tolerance label, and tolerance.
     *
     * @param valueLabel the label for the value
     * @param value the value of the input field
     * @param toleranceLabel the label for the tolerance
     * @param tolerance the tolerance of the input field
     */
    public InputField(String valueLabel, double value, String toleranceLabel, double tolerance) {
        this.valueLabel = valueLabel;
        this.value = new SimpleStringProperty("");
        this.value.setValue(String.valueOf(value));

        this.toleranceLabel = toleranceLabel;
        this.tolerence = new SimpleStringProperty("");
        this.tolerence.setValue(String.valueOf(tolerance));
    }

    /**
     * Returns the value property of the input field.
     *
     * @return the value property of the input field
     */
    public Property<String> valueProperty() {
        return value;
    }

    /**
     * Returns the tolerance property of the input field.
     *
     * @return the tolerance property of the input field
     */
    public Property<String> tolerenceProperty() {
        return tolerence;
    }

    /**
     * Returns the value of the input field as a double.
     *
     * @return the value of the input field
     */
    public double getValue() {
        return Double.parseDouble(value.getValue());
    }

    /**
     * Checks if the input field is using tolerance.
     *
     * @return true if the input field has a tolerance, false otherwise
     */
    public boolean usingTolerance() {
        return tolerence != null;
    }

    /**
     * Returns the tolerance of the input field as a double.
     *
     * @return the tolerance of the input field or NaN if not using tolerance
     */
    public double getTolerence() {
        return usingTolerance() ? Double.parseDouble(tolerence.getValue()) : Double.NaN;
    }

    /**
     * Returns the value label of the input field.
     *
     * @return the value label of the input field
     */
    public String getValueLabel() {
        return valueLabel;
    }

    /**
     * Returns the tolerance label of the input field.
     *
     * @return the tolerance label of the input field
     */
    public String getToleranceLabel() {
        return toleranceLabel;
    }

    /**
     * The label for the value of the input field.
     */
    private final String valueLabel;

    /**
     * The value of the input field.
     */
    private Property<String> value;

    /**
     * The label for the tolerance of the input field.
     */
    private final String toleranceLabel;

    /**
     * The tolerance of the input field.
     */
    private Property<String> tolerence;
}
