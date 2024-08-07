package com.quantum.utils;

/**
 * Represents a color with RGBA components.
 * <p>
 * The {@code Color} class provides a way to represent and manipulate colors using red, green, blue, and alpha
 * components. Each component is represented as a float value, where red, green, and blue values range from 0.0 to 1.0,
 * and alpha represents the opacity with a range of 0.0 (fully transparent) to 1.0 (fully opaque).
 * </p>
 * <p>
 * This class provides constructors to initialize color objects with different levels of precision and methods to
 * set and get the individual color components.
 * </p>
 * 
 * @author RAZANAKOLONA Erwan-Matthieu Liantsoa
 * @version 1.0
 * @since 1.0
 */
public class Color {
    
    private float   red,
                    green,
                    blue,
                    alpha;

    /**
     * Constructs a {@code Color} object with default values (black color with full opacity).
     * <p>
     * The color is initialized to red = 0.0, green = 0.0, blue = 0.0, and alpha = 1.0.
     * </p>
     * 
     * @since 1.0
     */
    public Color() {
        this.red = this.green = this.blue = 0.0f;
        this.alpha = 1.0f;
    }

    /**
     * Constructs a {@code Color} object with equal red, green, and blue values.
     * <p>
     * The color is initialized to red = green = blue = {@code f} and alpha = 1.0.
     * </p>
     * 
     * @param f the value for red, green, and blue components
     * 
     * @since 1.0
     */
    public Color(float f) {
        this(f, f, f);
    }

    /**
     * Constructs a {@code Color} object with specified red, green, and blue values.
     * <p>
     * The color is initialized with the given red, green, and blue values, and alpha = 1.0.
     * </p>
     * 
     * @param red   the red component (0.0 to 1.0)
     * @param green the green component (0.0 to 1.0)
     * @param blue  the blue component (0.0 to 1.0)
     * 
     * @since 1.0
     */
    public Color(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = 1.0f;
    }

    /**
     * Constructs a {@code Color} object with specified red, green, blue, and alpha values.
     * <p>
     * The color is initialized with the given red, green, blue, and alpha values.
     * </p>
     * 
     * @param red   the red component (0.0 to 1.0)
     * @param green the green component (0.0 to 1.0)
     * @param blue  the blue component (0.0 to 1.0)
     * @param alpha the alpha component (0.0 to 1.0)
     * 
     * @since 1.0
     */
    public Color(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    /**
     * Sets the color components.
     * <p>
     * This method sets the red, green, and blue components of the color, while keeping the current alpha value.
     * </p>
     * 
     * @param red   the red component (0.0 to 1.0)
     * @param green the green component (0.0 to 1.0)
     * @param blue  the blue component (0.0 to 1.0)
     * 
     * @since 1.0
     */
    public void setColour(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Sets the color components.
     * <p>
     * This method sets the red, green, blue, and alpha components of the color.
     * </p>
     * 
     * @param red   the red component (0.0 to 1.0)
     * @param green the green component (0.0 to 1.0)
     * @param blue  the blue component (0.0 to 1.0)
     * @param alpha the alpha component (0.0 to 1.0)
     * 
     * @since 1.0
     */
    public void setColour(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    /**
     * Sets the red component of the color.
     * 
     * @param red the red component (0.0 to 1.0)
     * 
     * @since 1.0
     */
    public void setRed(float red) {
        this.red = red;
    }

    /**
     * Sets the green component of the color.
     * 
     * @param green the green component (0.0 to 1.0)
     * 
     * @since 1.0
     */
    public void setGreen(float green) {
        this.green = green;
    }

    /**
     * Sets the blue component of the color.
     * 
     * @param blue the blue component (0.0 to 1.0)
     * 
     * @since 1.0
     */
    public void setBlue(float blue) {
        this.blue = blue;
    }

    /**
     * Sets the alpha component of the color.
     * 
     * @param alpha the alpha component (0.0 to 1.0)
     * 
     * @since 1.0
     */
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    /**
     * Returns the red component of the color.
     * 
     * @return the red component (0.0 to 1.0)
     * 
     * @since 1.0
     */
    public float getRed() {
        return red;
    }

    /**
     * Returns the green component of the color.
     * 
     * @return the green component (0.0 to 1.0)
     * 
     * @since 1.0
     */
    public float getGreen() {
        return green;
    }

    /**
     * Returns the blue component of the color.
     * 
     * @return the blue component (0.0 to 1.0)
     * 
     * @since 1.0
     */
    public float getBlue() {
        return blue;
    }

    /**
     * Returns the alpha component of the color.
     * 
     * @return the alpha component (0.0 to 1.0)
     * 
     * @since 1.0
     */
    public float getAlpha() {
        return alpha;
    }


}
