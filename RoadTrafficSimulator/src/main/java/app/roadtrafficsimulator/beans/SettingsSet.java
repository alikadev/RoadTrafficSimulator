package app.roadtrafficsimulator.beans;

import java.util.HashMap;
import java.util.Map;

public class SettingsSet {
    public SettingsSet(String name) {
        this.name = name;
        settings = new HashMap<>();
    }

    public SettingsSet(String name, Map<String, Double> settings) {
        this.settings = settings;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map<String, Double> getSettings() {
        return settings;
    }

    private Map<String, Double> settings;
    private String name;
}
