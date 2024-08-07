package com.quantum.ui.event;

import static com.quantum.ui.event.QEMouseEvent.getQEMouseWheelEvent;

import org.lwjgl.glfw.GLFWScrollCallbackI;

/**
 * Provides an abstraction layer for handling mouse wheel scroll events by implementing {@link GLFWScrollCallbackI}.
 *
 * <p>This class abstracts the handling of mouse wheel scroll events from GLFW by implementing
 * {@link GLFWScrollCallbackI} and converting the raw GLFW scroll events into higher-level
 * {@link QEMouseEvent} objects. It simplifies the interaction with GLFW by providing the
 * {@link #mouseWheelMoved(QEMouseEvent)} method, which can be overridden for custom event handling.</p>
 *
 * <p>In detail:</p>
 * <ul>
 *   <li>{@link #invoke(long, double, double)}: Processes raw mouse wheel scroll events from GLFW and
 *       delegates the event to {@link #mouseWheelMoved(QEMouseEvent)}.</li>
 *   <li>{@link #mouseWheelMoved(QEMouseEvent)}: Intended to be overridden to handle mouse wheel scroll events
 *       in a custom manner.</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * glfwSetScrollCallback(window.getWindowHandler(), new QEMouseWheelListener() {
 *
 *           @Override
 *           public void mouseWheelMoved(QEMouseEvent e) {
 *               //Handle scroll event
 *           }
 *           
 *       });
 * }
 * </pre>
 * 
 * @author RAZANAKOLONA Erwan-Matthieu Liantsoa
 * @version 1.0
 * @since 1.0
 *
 * @see GLFWScrollCallbackI
 * @see QEMouseEvent
 * @see QEAbstractListener
 */
public non-sealed class QEMouseWheelListener extends QEAbstractListener implements GLFWScrollCallbackI {

    /**
     * Handles raw mouse wheel scroll events from GLFW and delegates them to the appropriate method.
     * 
     * @param window The handle to the window where the event occurred.
     * @param xoffset The horizontal scroll amount. Typically used for horizontal scrolling.
     * @param yoffset The vertical scroll amount. Typically used for vertical scrolling.
     * 
     * @version 1.0
     * @since 1.0
     */
    @Override
    public void invoke(long window, double xoffset, double yoffset) {
        mouseWheelMoved(getQEMouseWheelEvent(window, xoffset, yoffset));
    }

    /**
     * Called when the mouse wheel is scrolled. This method can be overridden to provide custom handling
     * for mouse wheel scroll events.
     * 
     * @param e The {@link QEMouseEvent} representing the mouse wheel scroll event.
     * 
     * @version 1.0
     * @since 1.0
     */
    public void mouseWheelMoved(QEMouseEvent e) {}

}
