package com.quantum.ui.event;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static com.quantum.ui.event.QEMouseEvent.getQEMouseEvent;

import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

/**
 *Provides an abstraction layer for handling mouse button events by implementing {@link GLFWMouseButtonCallbackI}.
 *
 * <p>This class abstracts the handling of mouse button events from GLFW by implementing
 * {@link GLFWMouseButtonCallbackI} and converting the raw GLFW mouse button events into higher-level
 * {@link QEMouseEvent} objects. It simplifies the interaction with GLFW by providing
 * {@link #isPressed(QEMouseEvent)} and {@link #isReleased(QEMouseEvent)} methods that are easier to use
 * and extend for custom event handling.</p>
 *
 * <p>In detail:</p>
 * <ul>
 *   <li>{@link #invoke(long, int, int, int)}: Processes raw mouse button events from GLFW. Based on
 *       the action (button press or release), it delegates the event to either {@link #isPressed(QEMouseEvent)}
 *       or {@link #isReleased(QEMouseEvent)}.</li>
 *   <li>{@link #isPressed(QEMouseEvent)}: Intended to be overridden to handle mouse button press events
 *       in a custom manner.</li>
 *   <li>{@link #isReleased(QEMouseEvent)}: Intended to be overridden to handle mouse button release events
 *       in a custom manner.</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * 
 * glfwSetMouseButtonCallback(window, new MyMouseListener() {
 * 
 *     @Override
 *     public void isPressed(QEMouseEvent e) {
 *          //Handle mouse button press
 *     }
 *
 *     @Override
 *     public void isReleased(QEMouseEvent e) {
 *          //Handle mouse button release
 *     }
 * 
 * });
 * }
 * </pre>
 *
 * @author RAZANAKOLONA Erwan-Matthieu Liantsoa
 * @since 1.0
 * @version 1.0
 * 
 * @see GLFWMouseButtonCallbackI
 * @see QEMouseEvent
 * @see QEAbstractListener
 */
public non-sealed class QEMouseActionListener extends QEAbstractListener implements GLFWMouseButtonCallbackI{

    private boolean[] buttonStates = new boolean[GLFW_MOUSE_BUTTON_LAST + 1];

    /**
     * Handles raw mouse button events from GLFW and delegates them to the appropriate method
     * based on the action (button press or release). This method converts GLFW-specific events
     * into higher-level {@link QEMouseEvent} objects.
     * 
     * @param window The handle to the window where the event occurred.
     * @param button The mouse button code that was pressed or released.
     * @param action The action of the event (GLFW_PRESS or GLFW_RELEASE).
     * @param mods A bitfield representing the modifier keys that were active.
     * 
     * @version 1.0
     * @since 1.0
     */
    @Override
    public void invoke(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            buttonStates[button] = true;
            isPressed(getQEMouseEvent(window ,button, mods));
        } else if (action == GLFW_RELEASE) {
            if (buttonStates[button]) {
                isClicked(getQEMouseEvent(window, button, mods));
                buttonStates[button] = false;
            }
            isReleased(getQEMouseEvent(window, button, mods));
        }
    }

    /**
     * Called when a mouse button is clicked. This method can be overridden to provide custom handling
     * for mouse button clicked events at a higher abstraction level.
     * 
     * @param e The {@link QEMouseEvent} representing the mouse button clicked event.
     * 
     * @since 1.0
     */
    public void isClicked(QEMouseEvent qeMouseEvent) {}

    /**
     * Called when a mouse button is pressed. This method can be overridden to provide custom handling
     * for mouse button press events at a higher abstraction level.
     * 
     * @param e The {@link QEMouseEvent} representing the mouse button press event.
     * 
     * @since 1.0
     */
    public void isReleased(QEMouseEvent e) {}

    /**
     * Called when a mouse button is released. This method can be overridden to provide custom handling
     * for mouse button release events at a higher abstraction level.
     * 
     * @param e The {@link QEMouseEvent} representing the mouse button release event.
     * 
     * @since 1.0
     */
    public void isPressed(QEMouseEvent e) {}
    
}
