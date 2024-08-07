package com.quantum.system;

import static com.quantum.system.OSInfo.getOsName;
import static com.quantum.utils.logging.HardwareLogging.getLogger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CPUMonitor extends Thread {

    private static final Logger LOGGER = getLogger(CPUMonitor.class);
    
    private static final String[] WINDOWS_CPU_INFO_COMMAND = {"wmic", "path", "win32_VideoController", "get", "name"};
    private static final String LINUX_CPU_INFO_COMMAND = "lscpu";

    private static final String UNSUPPORTED_MESSAGE = "Unsupported Operating System";

    private static final String OS_NAME = getOsName();

    private static final OperatingSystemMXBean osMxBean = ManagementFactory.getOperatingSystemMXBean();

    @Override
    public String toString() {
        if ( OS_NAME.contains("win") ) {
            return windows();
        } else if( OS_NAME.contains("max") ) {
            return macOS();
        } else if ( OS_NAME.contains("nix") || OS_NAME.contains("nux") || OS_NAME.contains("aix") ) {
            return linux();
        }
        return UNSUPPORTED_MESSAGE;
    }

    public void log() {
        LOGGER.info("CPU: " + toString());;
    }

    private String linux() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(LINUX_CPU_INFO_COMMAND);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Model name:")) {
                    return line.substring(11).trim();
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "An exception occurred", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    private String macOS() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'macOS'");
    }

    private String windows() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(WINDOWS_CPU_INFO_COMMAND);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Model name:")) {
                    int index = line.indexOf("Model name:");
                    return line.substring(index).trim();
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "An exception occurred", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void run() {
        while (true) {
            double systemCPULoad = osMxBean.getSystemLoadAverage() / osMxBean.getAvailableProcessors();
            LOGGER.info("System CPU Load: " + String.format("%.2f", (systemCPULoad * 100)) + "%");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            };
        }
    }

}
