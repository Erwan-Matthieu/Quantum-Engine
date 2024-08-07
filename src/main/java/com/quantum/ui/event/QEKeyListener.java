package com.quantum.ui.event;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import org.lwjgl.glfw.GLFWKeyCallbackI;

/**
 * Manages keyboard events by implementing {@link GLFWKeyCallbackI} and extending {@link QEAbstractListener}.
 *
 * <p>This class provides an abstraction layer for handling keyboard events in applications
 * that use the GLFW library. It implements the {@link GLFWKeyCallbackI} interface to handle
 * raw key events and delegates these events to the methods defined in {@link QEKeyAdapter}.</p>
 *
 * <p>Key functionalities include:</p>
 * <ul>
 *   <li>{@link #invoke(long, int, int, int, int)}: Processes raw keyboard events from GLFW and
 *       calls the appropriate methods based on the action (key press or release).</li>
 *   <li>{@link #keyPressed(long, int, int, int)}: Method to be overridden to handle key press events.</li>
 *   <li>{@link #keyReleased(long, int, int, int)}: Method to be overridden to handle key release events.</li>
 * </ul>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 *  glfwSetKeyCallback(windowHandle, new QEKeyListener() {
 * 
 *      @Override
 *      public void keyPressed(long window, int key, int scancode, int mods) {
 *          // Handle key press event
 *      }
 * 
 *      @Override
 *      public void keyReleased(long window, int key, int scancode, int mods) {
 *          // Handle key release event
 *      }
 *
 *  });
 * }
 * </pre>
 * 
 * @author RAZANAKOLONA Erwan-Matthieu Liantsoa
 * @since 1.0
 * @version 1.0
 * 
 * @see GLFWKeyCallbackI
 * @see QEKeyAdapter
 * @see QEAbstractListener
 */
public non-sealed class QEKeyListener extends QEAbstractListener implements QEKeyAdapter, GLFWKeyCallbackI{

    /**
     * Handles raw keyboard events from GLFW and delegates them to the appropriate method
     * based on the action (key press or release).
     * 
     * @param window The handle to the window where the event occurred.
     * @param key The key code of the key involved in the event.
     * @param scancode The system-specific scancode of the key.
     * @param action The action of the event (GLFW_PRESS or GLFW_RELEASE).
     * @param mods A bitfield representing the modifier keys that were active.
     */
    @Override
    public void invoke(final long window, final int key, final int scancode, final int action, final int mods) {
        if (action == GLFW_PRESS) {
            keyPressed(window, key, scancode, mods);
        } else if (action == GLFW_RELEASE) {
            keyReleased(window, key, scancode, mods);
        }
    }

    @Override
    public void keyPressed(final long window, final int key, final int scancode, final int mods) {}

    @Override
    public void keyReleased(final long window, final int key, final int scancode, final int mods) {}
    
}
