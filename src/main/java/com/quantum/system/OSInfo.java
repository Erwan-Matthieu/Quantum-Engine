package com.quantum.system;

import static com.quantum.utils.logging.Logging.getLogger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OSInfo {
    
    private static final Logger LOGGER = getLogger(OSInfo.class);

    private static final String LINUX_DISTRO_VERSION_PATH = "/etc/os-release";

    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

    public static String getOsName() {
        return OS_NAME;
    }

    @Override
    public String toString() {
        if (isOnWindows()) {
            return "[Operating System]: Windows";
        } else if (isOnMac()) {
            return "[Operating System]: MacOS";
        } else if (isOnLinux()) {
            return "[Operating System]: Linux " + linuxInfo();
        }
        
        return "Unsupported Operating System";
    }

    private String linuxInfo() {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(LINUX_DISTRO_VERSION_PATH))) {
            String line;
            
            while ((line = reader.readLine()) != null ) {
                if (line.startsWith("NAME=") || line.startsWith("VERSION_ID=")) {
                    builder.append(line).append(" ");
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An exception occurred", e);
        }

        return builder.toString();
    }

    public static boolean isOnLinux() {
        return OS_NAME.contains("nux") || OS_NAME.contains("nix") || OS_NAME.contains("aix");
    }

    public static boolean isOnWindows() {
        return OS_NAME.contains("win");
    }

    public static boolean isOnMac() {
        return OS_NAME.contains("mac");
    }
}
