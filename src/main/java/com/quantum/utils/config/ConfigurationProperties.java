package com.quantum.utils.config;

import java.util.Properties;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.quantum.utils.logging.Logging.getLogger;

/**
 * Manages application configuration properties.
 * <p>
 * The {@link ConfigurationProperties} class is responsible for loading, accessing, modifying, and
 * saving configuration properties from a properties file located at
 * {@code /com/res/config/configuration.properties}. This class provides methods to retrieve and
 * update properties, as well as to save changes back to the properties file.
 * </p>
 *
 * <h2>Usage Example</h2>
 * <pre>
 * {@code
 * // Retrieve a property value
 * String appName = ConfigurationProperties.getProperty("app.name");
 * 
 * // Set a property value and save changes
 * ConfigurationProperties.setPropertyAndSave("app.version", "1.2.3");
 * }
 * </pre>
 * 
 * @author RAZANAKOLONA Erwan-Matthieu Liantsoa
 * @version 1.0
 * @since 1.0
 */
public class ConfigurationProperties {

    private static final Logger LOGGER = getLogger(ConfigurationProperties.class);
    
    private static final String CONFIGURATION_PROPERTIES_PATH = "/com/res/config/configuration.properties";

    private static final InputStream CONFIGURATION_PROPERTIES_STREAM = ConfigurationProperties.class.getResourceAsStream(CONFIGURATION_PROPERTIES_PATH);

    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(CONFIGURATION_PROPERTIES_STREAM);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An exception occured", e);
        }
    }

    /**
     * Retrieves the value of a property from the configuration.
     * 
     * @param key The key of the property to retrieve.
     * @return The value of the property associated with the specified key, or {@code null} if the
     *         property is not found.
     * 
     * @since 1.0
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Sets the value of a property in the configuration.
     * <p>
     * The value is converted to a string representation before being stored. Supported types include
     * {@code Integer}, {@code Long}, {@code Double}, {@code Float}, {@code Boolean}, and
     * {@code Character[]}.
     * </p>
     * 
     * @param key The key of the property to set.
     * @param value The value to set for the specified property.
     * 
     * @since 1.0
     */
    public static void setProperty(String key, Object value) {
        String valueToString = switch(value) {
            case Integer i -> String.valueOf(value);
            case Long l -> String.valueOf(l);
            case Double d -> String.valueOf(d);
            case Float f -> String.valueOf(f);
            case Boolean b -> String.valueOf(b);
            case Character[] c -> String.valueOf(c);

            default -> String.valueOf(value);
        };
        properties.setProperty(key, valueToString);
    }

    /**
     * Saves the current configuration properties to the properties file.
     * <p>
     * This method writes the properties to the file specified by {@link #CONFIGURATION_PROPERTIES_PATH}.
     * If an error occurs during saving, an error message is logged.
     * </p>
     * 
     * @since 1.0
     */
    public static void saveConfiguration() {
        try (FileOutputStream out = new FileOutputStream(new File(ConfigurationProperties.class.getResource(CONFIGURATION_PROPERTIES_PATH).toURI()))) {
            properties.store(out, "Configuration Properties");
            LOGGER.info("Successfully stored configuration modification");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An exception occurred", e);
        }
    }

    /**
     * Sets the value of a property and immediately saves the configuration to the properties file.
     * <p>
     * This method combines {@link #setProperty(String, Object)} and {@link #saveConfiguration()}.
     * </p>
     * 
     * @param key The key of the property to set.
     * @param value The value to set for the specified property.
     * 
     * @since 1.0
     */
    public static void setPropertyAndSave(String key, Object value) {
        setProperty(key, value);
        saveConfiguration();
    }
}
