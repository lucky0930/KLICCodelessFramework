package com.test.automation.common.framework;

import com.test.automation.common.framework.YamlConfiguration;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.MapConfiguration;

import java.util.*;

/**
 * Yaml enabled, configurable Settings object based on Apache Commons-Configuration.
 */
@SuppressWarnings("unchecked")
public class Settings {
    private static final String DEFAULT_SETTINGS_PROPERTY = "sehelper.settings";
    private static final String DEFAULT_SETTINGS_PATH = System.getProperty("user.dir")+"\\resources\\defaults.yml";

    private CompositeConfiguration settings;
    private final String yamlPath;

    /**
     * Creates a Settings object that uses the default yaml file.
     * <p/>
     * Value of sehelper.settings or if unset/empty, defaults.yml
     *
     * @throws ConfigurationException if failed to load yaml configuration file
     */
    public Settings() throws ConfigurationException {
        this.yamlPath = getSystemPropertySafe(DEFAULT_SETTINGS_PROPERTY, DEFAULT_SETTINGS_PATH);
        initialize();
    }

    /**
     * Creates a Settings object that uses the provided yaml file.
     *
     * @param yamlPath Relative or Absolute path to a yaml file to use
     * @throws ConfigurationException if failed to load yaml configuration file
     */
    public Settings(String yamlPath) throws ConfigurationException {
        this.yamlPath = yamlPath;
        initialize();
    }

    /**
     * Create a Settings object that will point to the yaml file referenced by the SystemProperty specified, or if that
     * is null/empty, points at the provided default yaml path.
     *
     * @param settingsProperty System property to read yaml path from
     * @param defaultYamlPath  yaml path to use if system property not set/empty
     * @throws ConfigurationException if failed to load yaml configuration file
     */
    public Settings(String settingsProperty, String defaultYamlPath) throws ConfigurationException {
        this.yamlPath = getSystemPropertySafe(settingsProperty, defaultYamlPath);
        initialize();
    }

    /**
     * Works like System.getProperty(name,default), but treats an empty string as unset.  Enables better integration
     * with maven/surefire.
     *
     * @param name         Name of system property
     * @param defaultValue Value if system property unset or empty
     * @return value of system property, or default if unset or empty
     */
    private static String getSystemPropertySafe(String name, String defaultValue) {
        String sysProp = System.getProperty(name);
        return (sysProp != null && !sysProp.isEmpty()) ? sysProp : defaultValue;
    }


    /**
     * Constructs a new CompositeConfiguration object form the current yamlFile path
     *
     * @throws ConfigurationException
     */
    private void initialize() throws ConfigurationException {
        CompositeConfiguration config = new CompositeConfiguration();
        config.addConfiguration(new MapConfiguration(getSystemPropertiesMapSafe()));
        config.addConfiguration(new YamlConfiguration(yamlPath));
        this.settings = config;
    }

    /**
     * Returns all the non-empty string system properties.
     *
     * @return Properties object with non-empty string system properties
     */
    private Map<String, Object> getSystemPropertiesMapSafe() {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Map.Entry<Object, Object> e : System.getProperties().entrySet()) {
            if (e.getKey() instanceof String) {
                if (e.getValue() instanceof String && !((String)e.getValue()).isEmpty())
                map.put((String) e.getKey(), e.getValue());
            }
        }
        return map;
    }

    /**
     * Return a string value from the settings.
     *
     * @param name Name of the String value to load
     * @return Value of the string, or null if it doesn't exist
     */
    public String value(String name) {
        return settings.getString(settings.getSubstitutor().replace(name));
    }

    /**
     * Return a integer value from the settings.
     *
     * @param name Name of the Integer value to load
     * @return Value of the Integer, or throws an exception if can't convert to an int
     */
    public int asInt(String name) {
        return settings.getInt(settings.getSubstitutor().replace(name));
    }

    /**
     * Return a boolean value from the settings.
     *
     * @param name Name of the Boolean value to load
     * @return Value of the Boolean, or throws an exception if can't convert to a boolean
     */
    public boolean asBoolean(String name) {
        return settings.getBoolean(settings.getSubstitutor().replace(name));
    }

    /**
     * Return a List from the settings.
     *
     * @param name Name of the list to load
     * @return The referenced list, or null if it doesn't exist
     */
    public List<String> list(String name) {
        List<String> items = new ArrayList<String>();
        for (Object item : settings.getList(settings.getSubstitutor().replace(name)))
            items.add(item.toString());
        return items;
    }

    /**
     * Return a Map from the settings.
     *
     * @param name Name of the map to load
     * @return The referenced map, or null if it doesn't exist
     */
    public Map<String, String> map(String name) {
        Map<String, String> map = new HashMap<String, String>();
        Configuration subset = settings.subset(settings.getSubstitutor().replace(name));
        Iterator<String> subsetKeys = subset.getKeys();
        while (subsetKeys.hasNext()) {
            String property = subsetKeys.next();
            map.put(property, subset.getString(property));
        }
        return map;
    }

    @Override
    public String toString() {
        return String.format("Settings{path='%s'}", yamlPath);
    }
}