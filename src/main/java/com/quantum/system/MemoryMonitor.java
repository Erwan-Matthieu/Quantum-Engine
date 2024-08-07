package com.quantum.system;

import static com.quantum.utils.logging.HardwareLogging.getLogger;

import java.util.logging.Logger;

public class MemoryMonitor extends Thread {

    private static final Logger LOGGER = getLogger(MemoryMonitor.class);
    
    private Runtime runtime = Runtime.getRuntime();

    @Override
    public void run() {
        
        while (true) {
            long    totalMemory = runtime.totalMemory(),
                    freeMemory = runtime.freeMemory(),
                    maxMemory = runtime.maxMemory(),
                    usedMemory = totalMemory - freeMemory;

            LOGGER.info("Total Memory: " + totalMemory / 1024 / 1024 + " MB\n"
                        + "\t\tFree Memory: " + freeMemory / 1024 / 1024 + " MB\n"
                        + "\t\tMax Memory: " + maxMemory / 1024 / 1024 + " MB\n"
                        + "\t\tUsed Memory: " + usedMemory / 1024 / 1024 + " MB");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
