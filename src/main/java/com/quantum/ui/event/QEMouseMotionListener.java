package com.quantum.ui.event;

import static com.quantum.ui.event.QEMouseEvent.getQEMouseEvent;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;

/**
 * Provides an abstraction layer for handling mouse cursor motion events by implementing {@link GLFWCursorPosCallbackI}.
 *
 * <p>This class abstracts the handling of mouse cursor position events from GLFW by implementing
 * {@link GLFWCursorPosCallbackI} and converting the raw GLFW cursor position events into higher-level
 * {@link QEMouseEvent} objects. It simplifies the interaction with GLFW by providing
 * {@link #mouseMoved(QEMouseEvent)}, and {@link #isEntered(QEMouseEvent)}
 * methods that are easier to use and extend for custom event handling.</p>
 *
 * <p>In detail:</p>
 * <ul>
 *   <li>{@link #invoke(long, double, double)}: Processes raw mouse cursor position events from GLFW and
 *       delegates the event to {@link #mouseMoved(QEMouseEvent)}, or {@link #isEntered(QEMouseEvent)},based on whether the cursor is within the window bounds.</li>
 *   <li>{@link #mouseMoved(QEMouseEvent)}: Intended to be overridden to handle mouse cursor movement events
 *       in a custom manner.</li>
 *   <li>{@link #isEntered(QEMouseEvent)}: Intended to be overridden to handle events where the mouse enters
 *       the window area.</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * glfwSetCursorPosCallback(window, new MyMouseMotionListener() {
 *      
 *      @Override
 *      public void mouseMoved(QEMouseEvent e) {
 *          //Handle cursor move in the window
 *      }
 * 
 *      @Override
 *      public void isEntered(QEMouseEvent e) {
 *          //Handle cursor in the window
 *      }
 * 
 * });
 * }
 * </pre>
 * 
 * @author RAZANAKOLONA Erwan-Matthieu Liantsoa
 * @version 1.0
 * @since 1.0
 *
 * @see GLFWCursorPosCallbackI
 * @see QEMouseEvent
 * @see QEAbstractListener
 * 
 */
public non-sealed class QEMouseMotionListener extends QEAbstractListener implements GLFWCursorPosCallbackI{

    /**
     * Handles raw mouse cursor position events from GLFW and delegates them to the appropriate method
     * based on whether the cursor is within the window bounds.
     * 
     * @param window The handle to the window where the event occurred.
     * @param xpos The x-coordinate of the cursor position.
     * @param ypos The y-coordinate of the cursor position.
     * 
     * @since 1.0
     */
    @Override
    public void invoke(long window, double xpos, double ypos) {
        QEMouseEvent e = getQEMouseEvent(window, xpos, ypos);

        if (e.isInWindow()) {
            mouseMoved(e);
            isEntered(e);
        }

    }

    /**
     * Called when the mouse is moved within the window. This method can be overridden to provide custom handling
     * for mouse movement events.
     * 
     * @param e The {@link QEMouseEvent} representing the mouse movement event.
     * 
     * @since 1.0
     */
    public void mouseMoved(QEMouseEvent e) {}

    /**
     * Called when the mouse cursor enters the window area. This method can be overridden to provide custom handling
     * for when the mouse cursor enters the window.
     * 
     * @param e The {@link QEMouseEvent} representing the event of the cursor entering the window.
     * 
     * @since 1.0
     */
    public void isEntered(QEMouseEvent e) {}

}
