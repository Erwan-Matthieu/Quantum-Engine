package com.quantum.ui.event;

/**
 * Defines all possible methods for handling keyboard events through the {@link com.quantum.ui.event.QEKeyAdapter} interface.
 * 
 *  <p>This interface is designed to provide a comprehensive set of methods for handling keyboard events
 * in a flexible and modular manner. It extends the functionality of {@link com.quantum.ui.event.QEKeyListener}
 * by offering a complete suite of event-handling methods that can be implemented to manage keyboard
 * interactions within an application.</p>
 * 
 * <p>Key methods provided by this interface include:</p>
 * <ul>
 *   <li>{@link #keyPressed(long, int, int, int)} - Called when a key is pressed.</li>
 *   <li>{@link #keyReleased(long, int, int, int)} - Called when a key is released.</li>
 * </ul>
 * 
 * <p>Implementing this interface allows for the creation of custom handlers that can respond to
 * various keyboard events. For example, you might use it to capture user input, navigate through
 * application controls, or trigger specific actions based on key events.</p>
 * 
 * <p>Usage example:</p>
 * <pre>
 * {@code
 * public class MyKeyHandler implements QEKeyAdapter {
 * 
 *     @Override
 *     public void keyPressed(long window, int key, int scancode, int mods) {
 *         // Handle key press event
 *     }
 *
 *     @Override
 *     public void keyReleased(long window, int key, int scancode, int mods) {
 *         // Handle key release event
 *     }
 *
 * }
 * }
 * </pre>
 * 
 * @author RAZANAKOLONA Erwan-Matthieu Liantsoa
 * @version 1.0
 * @since 1.0
 */
public interface QEKeyAdapter {

    /**
    * Handles the key press event.
    * 
    * <p>This method is called when a key is pressed. It allows you to respond to key press
    * events by implementing custom logic based on the key that was pressed.</p>
    *
    * @param window The handle to the window where the key press event occurred. This can be used
    *               to identify which window the event is related to, especially in applications
    *               with multiple windows.
    * @param key The key code of the key that was pressed. This is an integer value representing
    *            the specific key that was released. Key codes are defined by the GLFW library
    *            and can be mapped to specific keys or characters.
    * @param scancode The system-specific scancode of the key that was pressed. This value provides
    *                 additional information about the key's position on the keyboard, which can be
    *                 useful for handling keyboard layouts or identifying key locations.
    * @param mods A bitfield representing the modifier keys that were active when the key was pressed.
    *             This can include shift, control, alt, and other modifier keys. It helps in determining
    *             the state of modifier keys at the time of the event.
    * @since 1.0
    */
    void keyPressed(long window, int key, int scancode, int mods);

    /**
    * Handles the key release event.
    * 
    * <p>This method is called when a key is released. It allows you to respond to key release
    * events by implementing custom logic based on the key that was released.</p>
    *
    * @param window The handle to the window where the key release event occurred. This can be used
    *               to identify which window the event is related to, especially in applications
    *               with multiple windows.
    * @param key The key code of the key that was released. This is an integer value representing
    *            the specific key that was released. Key codes are defined by the GLFW library
    *            and can be mapped to specific keys or characters.
    * @param scancode The system-specific scancode of the key that was released. This value provides
    *                 additional information about the key's position on the keyboard, which can be
    *                 useful for handling keyboard layouts or identifying key locations.
    * @param mods A bitfield representing the modifier keys that were active when the key was released.
    *             This can include shift, control, alt, and other modifier keys. It helps in determining
    *             the state of modifier keys at the time of the event.
    * @since 1.0
    */
    void keyReleased(long window, int key, int scancode, int mods);

}