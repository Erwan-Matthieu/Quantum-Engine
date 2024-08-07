package com.quantum.system;

import static com.quantum.utils.logging.Logging.getLogger;
import static org.lwjgl.opengl.GL11.GL_RENDERER;
import static org.lwjgl.opengl.GL11.GL_VENDOR;
import static org.lwjgl.opengl.GL11.glGetString;

import java.util.logging.Logger;

public class GPUMonitor {
    
    private static final Logger LOGGER = getLogger(GPUMonitor.class);

    private final String VENDOR = glGetString(GL_VENDOR);
    private final String RENDERER = glGetString(GL_RENDERER);

    @Override
    public String toString() {
        return String.format("[VENDOR]: %s [RENDERER]: %s", VENDOR, RENDERER);
    }

    public void log() {
        LOGGER.info("Vendor: " + VENDOR);
        LOGGER.info("RENDERER: " + RENDERER);
    }

    public String getVENDOR() {
        return VENDOR;
    }

    public String getRENDERER() {
        return RENDERER;
    }
    
}
