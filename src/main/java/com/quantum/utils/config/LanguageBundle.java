package com.quantum.utils.config;

import static com.quantum.utils.config.ConfigurationProperties.getProperty;
import static com.quantum.utils.logging.Logging.getLogger;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages a resource bundle for localized messages and other resources.
 * 
 *
 * The {@link com.quantum.utils.config.LanguageBundle} class simplifies internationalization
 * by providing access to localized messages according to the current language and locale.
 * It dynamically retrieves localized strings to enhance the user experience in various languages.
 *
 * <p>
 * This class loads and manages a {@link ResourceBundle} based on the language and country
 * specified in the configuration properties. It offers methods to fetch localized strings and
 * details about the current locale.
 * </p>
 * <p>
 * The resource bundle is initialized using the base name provided to the constructor and the
 * locale derived from the {@code LANGUAGE} and {@code COUNTRY} properties in the configuration.
 * </p>
 *
 *
 * <h2>Usage Example</h2>
 * <pre>
 * {@code
 * LanguageBundle bundle = new LanguageBundle("messages");
 * String welcomeMessage = bundle.getString("welcome.message");
 * System.out.println(welcomeMessage);
 * }
 * </pre>
 * 
 * @author RAZANAKOLONA Erwan-Matthieu Liantsoa
 * @version 1.0
 * @since 1.0
 */
public class LanguageBundle {

    private static final Logger LOGGER = getLogger(LanguageBundle.class);

    private static final Locale locale = Locale.of(getProperty("LANGUAGE"), getProperty("COUNTRY"));

    private ResourceBundle bundle;

    /**
     * Constructs a {@link LanguageBundle} with the specified base name.
     * <p>
     * The base name is used to locate the resource bundle for the given locale. The resource bundle
     * is loaded from the path "com/res/lang/" and uses {@link LanguageBundleControl} to control
     * the resource loading behavior.
     * </p>
     * 
     * @param baseName The base name of the resource bundle, which is the name of the properties files
     *                 (excluding the file extension).
     * @throws RuntimeException If an exception occurs while loading the resource bundle.
     * 
     * @since 1.0
     */
    public LanguageBundle(String baseName) {
        try {
            this.bundle = ResourceBundle.getBundle("com/res/lang/" + baseName, locale, new LanguageBundleControl());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An exception occurred", e);
            throw e;
        }
    }

    /**
     * Retrieves a localized string for the specified key.
     * 
     * @param key The key for the desired string in the resource bundle.
     * @return The localized string associated with the specified key.
     * 
     * @since 1.0
     */
    public String getString(String key) {
        return bundle.getString(key);
    }

    /**
     * Returns the country code of the current locale.
     * 
     * @return The country code of the current locale.
     * 
     * @since 1.0
     */
    public String getCountry() {
        return locale.getCountry();
    }

    /**
     * Returns the language code of the current locale.
     * 
     * @return The language code of the current locale.
     * 
     * @since 1.0
     */
    public String getLanguage() {
        return locale.getLanguage();
    }

    /**
     * A custom {@link ResourceBundle.Control} implementation for managing resource bundle formats.
     * <p>
     * This control specifies that the resource bundles are in "java.properties" format and does not
     * provide fallback locales.
     * </p>
     * 
     * @author RAZANAKOLONA Erwan-Matthieu Liantsoa
     * @version 1.0
     * @since 1.0
     */
    private class LanguageBundleControl extends Control {

        @Override
        public List<String> getFormats(String baseName) {
            return List.of("java.properties");
        }

        @Override
        public Locale getFallbackLocale(String baseName, Locale locale) {
            return null;
        }
        
    }
    
}
