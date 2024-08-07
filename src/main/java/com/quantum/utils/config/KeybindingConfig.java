package com.quantum.utils.config;

import static com.quantum.utils.logging.Logging.getLogger;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeybindingConfig {
    
    private static final Logger LOGGER = getLogger(KeybindingConfig.class);

    private static final Properties PROPERTIES = new Properties();

    private static final String CONFIG_FILE = "/com/res/config/keybinding.properties";

    public KeybindingConfig() {
        loadConfig();
    }

    private void loadConfig() {
        try {
            PROPERTIES.load(this.getClass().getResourceAsStream(CONFIG_FILE));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Can't load keybinding config", e);
        }
    }

    public static int getKeybinding(String action) {
        return Integer.parseInt(PROPERTIES.getProperty(action));
    }

    public static void setKeybinding(String action, int key) {
        PROPERTIES.setProperty(action, String.valueOf(key));
        LOGGER.info("Action " + action + " is set to " + key + " key");
    }
}
