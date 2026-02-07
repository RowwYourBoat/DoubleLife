package me.rowan.doublelife.data.config;

public class ConfigEntry<T> {

    private T value;
    private final T defaultValue;
    private final String name;
    private final ConfigSection parent;

    public ConfigEntry(T defaultValue, String name, ConfigSection parent) {
        this.defaultValue = defaultValue;
        this.name = name;
        this.parent = parent;
    }

    public void setValue(T newValue) {
        this.value = newValue;
    }

    public T getValue() {
        return value;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public String getName() {
        return name;
    }

    public ConfigSection getParent() {
        return parent;
    }
}
