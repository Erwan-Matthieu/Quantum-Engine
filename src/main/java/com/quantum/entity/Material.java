package com.quantum.entity;

import org.joml.Vector4f;

import com.quantum.core.graphics.Texture;

public class Material {

    private static final Vector4f DEFAULT_COLOR = new Vector4f(1.0f);

    private Vector4f    ambiantColour,
                        diffuseColour,
                        specularColour;

    private float reflectance;

    private Texture texture;

    public Material() {
        this.ambiantColour = DEFAULT_COLOR;
        this.diffuseColour = DEFAULT_COLOR;
        this.specularColour = DEFAULT_COLOR;
        this.texture = null;
        this.reflectance = 0;
    }

    public Material(Vector4f colour, float reflectance) {
        this(colour, colour, colour, reflectance, null);
    }

    public Material(Texture texture) {
        this(DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR, 0, texture);
    }

    public Material(float reflectance, Texture texture) {
        this(DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR, reflectance, texture);
    }

    public Material(Vector4f ambiantColour, Vector4f diffuseColour, Vector4f specularColour, float reflectance,
            Texture texture) {
        this.ambiantColour = ambiantColour;
        this.diffuseColour = diffuseColour;
        this.specularColour = specularColour;
        this.reflectance = reflectance;
        this.texture = texture;
    }

    public Vector4f getAmbiantColour() {
        return ambiantColour;
    }

    public void setAmbiantColour(Vector4f ambiantColour) {
        this.ambiantColour = ambiantColour;
    }

    public Vector4f getDiffuseColour() {
        return diffuseColour;
    }

    public void setDiffuseColour(Vector4f diffuseColour) {
        this.diffuseColour = diffuseColour;
    }

    public Vector4f getSpecularColour() {
        return specularColour;
    }

    public void setSpecularColour(Vector4f specularColour) {
        this.specularColour = specularColour;
    }

    public float getReflectance() {
        return reflectance;
    }

    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public boolean isTextured() {
        return texture != null;
    }

}
