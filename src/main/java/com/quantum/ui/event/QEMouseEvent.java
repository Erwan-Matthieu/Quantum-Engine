package com.quantum.ui.event;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;

import java.awt.Point;
import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

/**
 * Represents a mouse event with details about the cursor position, mouse button, and scroll offsets.
 * <p>
 * This class provides various methods to access mouse event details such as the cursor position, mouse button state,
 * and scroll offsets. It uses GLFW's functions to obtain cursor position and window size.
 * </p>
 * <p>
 * This class includes static factory methods to create instances of {@link QEMouseEvent} based on different types of mouse
 * events. It maintains a single static instance of {@link QEMouseEvent} for reuse.
 * </p>
 *
 * @author RAZANAKOLONA Erwan-Matthieu Liantsoa
 * @version 1.0
 * @since 1.0
 *
 * @see org.lwjgl.glfw.GLFW
 */
public class QEMouseEvent {
    
    private static QEMouseEvent mouseEvent;

    /**
     * Returns a singleton instance of {@link QEMouseEvent} for a given window.
     * 
     * @param window The handle to the window where the event occurred.
     * @return A {@link QEMouseEvent} instance.
     * 
     * @since 1.0
     */
    public static synchronized QEMouseEvent getQEMouseEvent(long window) {
        if (mouseEvent != null) {
            return mouseEvent;
        } else {
            return mouseEvent = new QEMouseEvent(window);
        }
    }

     /**
     * Returns a singleton instance of {@link QEMouseEvent} for a given window, mouse button and mods.
     * 
     * @param window The handle to the window where the event occurred.
     * @param button The mouse button involved in the event.
     * @param mods The modifier keys involved in the event
     * @return A {@link QEMouseEvent} instance.
     * 
     * @since 1.0
     */
    public static synchronized QEMouseEvent getQEMouseEvent(long window, int button, int mods) {
        if (mouseEvent != null) {
            mouseEvent.setButton(button);
            mouseEvent.setMods(mods);
            return mouseEvent;
        } else {
            return mouseEvent = new QEMouseEvent(window, button, mods);
        }
    }

    /**
     * Returns a singleton instance of {@link QEMouseEvent} for a given window and cursor position.
     * 
     * @param window The handle to the window where the event occurred.
     * @param xPos The x-coordinate of the cursor position.
     * @param yPos The y-coordinate of the cursor position.
     * @return A {@link QEMouseEvent} instance.
     * 
     * @since 1.0
     */
    public static synchronized QEMouseEvent getQEMouseEvent(long window, double xPos, double yPos) {
        if (mouseEvent != null) {
            mouseEvent.setxPos(xPos);
            mouseEvent.setyPos(yPos);
            return mouseEvent;
        } else {
           return mouseEvent = new QEMouseEvent(window, xPos, yPos);
        }
    }

    /**
     * Returns a singleton instance of {@link QEMouseEvent} for a given window and scroll offsets.
     * 
     * @param window The handle to the window where the event occurred.
     * @param xOffset The horizontal scroll offset.
     * @param yOffset The vertical scroll offset.
     * @return A {@link QEMouseEvent} instance.
     * 
     * @since 1.0
     */
    public static synchronized QEMouseEvent getQEMouseWheelEvent(long window, double xOffset, double yOffset) {
        if (mouseEvent != null) {
            mouseEvent.setxOffset(xOffset);
            mouseEvent.setyOffset(yOffset);
            return mouseEvent;
        } else {
            mouseEvent = new QEMouseEvent(window);
            mouseEvent.setxOffset(xOffset);
            mouseEvent.setyOffset(yOffset);
           return mouseEvent;
        }
    }

    /**
     * Sets the singleton instance of {@link QEMouseEvent}.
     * 
     * @param mouseEvent The {@link QEMouseEvent} instance to set.
     * 
     * @since 1.0
     */
    public static void setMouseEvent(QEMouseEvent mouseEvent) {
        QEMouseEvent.mouseEvent = mouseEvent;
    }

    private final long windowHanlder;

    private int button;

    private double  xPos,
                    yPos;

    private double  xOffset,
                    yOffset;

    private int mods;

    /**
     * Constructs a {@link QEMouseEvent} with a window handle.
     * 
     * @param windowHanlder The handle to the window where the event occurred.
     * 
     * @since 1.0
     */
    public QEMouseEvent(long windowHanlder) {
        this.windowHanlder = windowHanlder;
    }

    /**
     * Constructs a {@link QEMouseEvent} with a window handle and mouse button.
     * 
     * @param windowHanlder The handle to the window where the event occurred.
     * @param button The mouse button involved in the event.
     * 
     * @since 1.0
     */
    private QEMouseEvent(long windowHanlder, int button) {
        this(windowHanlder);
        this.button = button;
    }

    /**
     * Constructs a {@link QEMouseEvent} with a window handle, mouse button and the modifier keys
     * 
     * @param windowHanlder The handle to the window where the event occurred
     * @param button The mosue button involved in the event
     * @param mods The modifier keys involved in the event
     * 
     * @since 1.0
     */
    private QEMouseEvent(long windowHanlder, int button, int mods) {
        this(windowHanlder, button);
        this.mods = mods;
    }

    /**
     * Constructs a {@link QEMouseEvent} with a window handle and cursor position.
     * 
     * @param windowHanlder The handle to the window where the event occurred.
     * @param xPos The x-coordinate of the cursor position.
     * @param yPos The y-coordinate of the cursor position.
     * 
     * @since 1.0
     */
    private QEMouseEvent(long windowHanlder, double xPos, double yPos) {
        this.windowHanlder = windowHanlder;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     * Returns the current modifier keys
     * 
     * @return The current modifier keys
     */
    public int getMods() {
        return mods;
    }

    private void setMods(int mods) {
        this.mods = mods;
    }

    /**
     * Returns the horizontal scroll offset.
     * 
     * @return The horizontal scroll offset.
     * 
     * @since 1.0
     */
    public double getxOffset() {
        return xOffset;
    }

    /**
     * Returns the vertical scroll offset.
     * 
     * @return The vertical scroll offset.
     * 
     * @since 1.0
     */
    public double getyOffset() {
        return yOffset;
    }

    /**
     * Returns the current cursor position as a {@link Point}.
     * 
     * @return A {@link Point} representing the cursor position.
     * 
     * @since 1.0
     */
    public Point getPosition() {
        DoubleBuffer xPos = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer yPos = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(windowHanlder, xPos, yPos);
        
        return new Point((int) xPos.get(0), (int) yPos.get(0));
    }

    /**
     * Returns the x-coordinate of the cursor position.
     * 
     * @return The x-coordinate of the cursor position.
     * 
     * @since 1.0
     */
    public double getX() {
        return this.getPosition().getX();
    }

    /**
     * Returns the y-coordinate of the cursor position.
     * 
     * @return The y-coordinate of the cursor position.
     * 
     * @since 1.0
     */
    public double getY() {
        return this.getPosition().getY();
    }

    /**
     * Determines if the cursor position is within the window's bounds.
     * 
     * @return {@code true} if the cursor is within the window; {@code false} otherwise.
     * 
     * @since 1.0
     */
    public boolean isInWindow() {

        int[] width = new int[1];
        int[] height = new int[1];

        glfwGetWindowSize(windowHanlder, width, height);
        
        return xPos >= 0 && xPos <= width[0] && yPos >= 0 && yPos <= height[0];
    }

    /**
     * Returns the mouse button involved in the event.
     * 
     * @return The mouse button.
     * 
     * @since 1.0
     */
    public int getButton() {
        return button;
    }

    public long getWindowHanlder() {
        return windowHanlder;
    }

    private void setxOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    private void setyOffset(double yOffset) {
        this.yOffset = yOffset;
    }

    private void setxPos(double xPos) {
        this.xPos = xPos;
    }

    private void setyPos(double yPos) {
        this.yPos = yPos;
    }

    private void setButton(int button) {
        this.button = button;
    }

}
