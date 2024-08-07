package com.quantum.core;

import org.joml.Vector3f;

import com.quantum.core.graphics.Mesh;

public class GameItem {

    private Mesh mesh;

    private final Vector3f  position,
                            rotation;

    private float scale;

    public GameItem() {
        position = new Vector3f();
        rotation = new Vector3f();
        scale = 1;
    }

    public GameItem(Mesh mesh) {
        this();
        this.mesh = mesh;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Vector3f getPosition() {
        return position;
        
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

}
