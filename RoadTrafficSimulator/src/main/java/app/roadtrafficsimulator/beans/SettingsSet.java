package app.roadtrafficsimulator.beans;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a set of settings. A set is identified by a set name and a setting by its id-string.
 *
 * @author Elvin Kuci
 */
public class SettingsSet {
    /**
     * Create a set of settings without any prefilled setting.
     *
     * @param name The name of the set.
     */
    public SettingsSet(String name) {
        this.name = name;
        settings = new HashMap<>();
    }

    /**
     * Create a set of settings.
     *
     * @param name The name of the set.
     * @param settings The map of setting-id to value. (it will be referenced and not copied)
     */
    public SettingsSet(String name, Map<String, Double> settings) {
        this.settings = settings;
        this.name = name;
    }

    /**
     * Get the name of the set.
     *
     * @return The name of the set.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the map of setting-id to value.
     *
     * @return The map of setting-id to value.
     */
    public Map<String, Double> getSettings() {
        return settings;
    }

    /**
     * The map of setting-id to value of the set.
     */
    private final Map<String, Double> settings;

    /**
     * The name of the set if settings.
     */
    private final String name;
}
