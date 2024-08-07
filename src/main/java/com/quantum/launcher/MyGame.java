package com.quantum.launcher;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.quantum.core.GameItem;
import com.quantum.core.Scene;
import com.quantum.core.graphics.Renderer;
import com.quantum.core.graphics.Terrain;
import com.quantum.core.io.MouseInput;
import com.quantum.core.io.Window;
import com.quantum.core.logic.IGameLogic;
import com.quantum.entity.Camera;
import com.quantum.entity.light.DirectionalLight;
import com.quantum.entity.light.SceneLight;

public class MyGame implements IGameLogic {

    private static final float MOUSE_SENSITIVITY = 0.2f;

    private static final float CAMERA_POS_STEP = 0.05f;

    private final Vector3f cameraInc;

    private final Camera camera;

    private final Renderer renderer;

    private GameItem[] gameItems;

    private Scene scene;

    private Terrain terrain;

    public MyGame() {
        renderer = new Renderer();
        cameraInc = new Vector3f();
        camera = new Camera();
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);

        scene = new Scene();

        float terrainScale = 3;
        int terrainSize = 20;
        float minY = -0.1f;
        float maxY = 0.f;
        int textInc = 40;
        this.terrain = new Terrain(terrainSize, terrainScale, minY, maxY, "textures/output1.png", "textures/terrain.png", textInc);
        scene.setMeshMap(terrain.getGameItems());

        // Setup Lights
        setupLights();
        
        camera.getPosition().x = .0f;
        camera.getPosition().y = .0f;
        camera.getPosition().y = 0.2f;
        camera.getRotation().x = 10.f;
    }

    private void setupLights() {
        SceneLight sceneLight = new SceneLight();
        scene.setSceneLight(sceneLight);

        sceneLight.setAmbientLight(new Vector3f(1.0f, 1.0f, 1.0f));

        float lightIntensity = 1.0f;
        Vector3f lightPosition = new Vector3f(-1, 0, 0);
        sceneLight.setDirectionalLight(new DirectionalLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity));
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            cameraInc.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            cameraInc.y = 1;
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {
        if (mouseInput.isRightButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }

        Vector3f prevPos = new Vector3f(camera.getPosition());
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);

        if (camera.getPosition().y <= terrain.getHeight(camera.getPosition())) {
            camera.setPosition(prevPos.x, prevPos.y, prevPos.z);
        }

        // SceneLight sceneLight = scene.getSceneLight();

        // DirectionalLight directionalLight = sceneLight.getDirectionalLight();
        // lightAngle += 0.01f;
        // if (lightAngle > 90) {
        //     directionalLight.setIntensity(0);
        //     if (lightAngle >= 360) {
        //         lightAngle = -90;
        //     }
        //     sceneLight.getAmbientLight().set(0.3f, 0.3f, 0.4f);
        // } else if (lightAngle <= -80 || lightAngle >= 80) {
        //     float factor = 1 - (float) (Math.abs(lightAngle) - 80) / 10.0f;
        //     sceneLight.getAmbientLight().set(factor, factor, factor);
        //     directionalLight.setIntensity(factor);
        //     directionalLight.getColor().y = Math.max(factor, 0.9f);
        //     directionalLight.getColor().z = Math.max(factor, 0.5f);
        // } else {
        //     sceneLight.getAmbientLight().set(1, 1, 1);
        //     directionalLight.setIntensity(1);
        //     directionalLight.getColor().x = 1;
        //     directionalLight.getColor().y = 1;
        //     directionalLight.getColor().z = 1;
        // }
        // double angRad = Math.toRadians(lightAngle);
        // directionalLight.getDirection().x = (float) Math.sin(angRad);
        // directionalLight.getDirection().y = (float) Math.cos(angRad);
    }

    @Override
    public void render(Window window) {
        renderer.render(window, camera, scene);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        for (GameItem gameItem : gameItems) {
            gameItem.getMesh().cleanUp();
        }
    }

}
