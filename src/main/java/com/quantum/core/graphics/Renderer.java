package com.quantum.core.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import com.quantum.core.GameItem;
import com.quantum.core.Scene;
import com.quantum.core.io.Window;
import com.quantum.entity.Camera;
import com.quantum.entity.light.DirectionalLight;
import com.quantum.entity.light.PointLight;
import com.quantum.entity.light.SceneLight;
import com.quantum.entity.light.SpotLight;

import static com.quantum.core.io.RessourceLoader.loadResource;
import static org.lwjgl.opengl.GL11.*;

import java.util.List;
import java.util.Map;

public class Renderer {

    /**
     * Field of View in Radians
     */
    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;

    private static final int MAX_POINT_LIGHTS = 5;

    private static final int MAX_SPOT_LIGHTS = 5;

    private final Transformation transformation;

    private Shader sceneShader;

    private Shader skyBoxShader;

    private final float specularPower;

    public Renderer() {
        transformation = new Transformation();
        specularPower = 10f;
    }

    public void init(Window window) throws Exception {
        setupSkyBoxShader();
        setupSceneShader();
    }

    private void setupSkyBoxShader() throws Exception {
        skyBoxShader = new Shader();
        skyBoxShader.createVertexShader(loadResource("/com/res/shaders/SkyBox_Vertex.glsl"));
        skyBoxShader.createFragmentShader(loadResource("/com/res/shaders/SkyBox_Fragment.glsl"));
        skyBoxShader.link();

        // Create uniforms for projection matrix
        skyBoxShader.createUniform("projectionMatrix");
        skyBoxShader.createUniform("modelViewMatrix");
        skyBoxShader.createUniform("texture_sampler");
        skyBoxShader.createUniform("ambientLight");
    }

    private void setupSceneShader() throws Exception {
        // Create shader
        sceneShader = new Shader();
        sceneShader.createVertexShader(loadResource("/com/res/shaders/Scene_Vertex.glsl"));
        sceneShader.createFragmentShader(loadResource("/com/res/shaders/Scene_Fragment.glsl"));
        sceneShader.link();

        // Create uniforms for modelView and projection matrices and texture
        sceneShader.createUniform("projectionMatrix");
        sceneShader.createUniform("modelViewMatrix");
        sceneShader.createUniform("texture_sampler");
        // Create uniform for material
        sceneShader.createMaterialUniform("material");
        // Create lighting related uniforms
        sceneShader.createUniform("specularPower");
        sceneShader.createUniform("ambientLight");
        sceneShader.createPointLightListUniform("pointLights", MAX_POINT_LIGHTS);
        sceneShader.createSpotLightListUniform("spotLights", MAX_SPOT_LIGHTS);
        sceneShader.createDirectionalLightUniform("directionalLight");
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, Camera camera, Scene scene) {
        clear();

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        renderScene(window, camera, scene);
        
    }

    public void renderScene(Window window, Camera camera, Scene scene) {
        sceneShader.bind();

        // Update projection Matrix
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        sceneShader.setUniform("projectionMatrix", projectionMatrix);

        // Update view Matrix
        Matrix4f viewMatrix = transformation.getViewMatrix(camera);

        SceneLight sceneLight = scene.getSceneLight();
        renderLights(viewMatrix, sceneLight);

        sceneShader.setUniform("texture_sampler", 0);
        // Render each gameItem
        Map<Mesh, List<GameItem>> mapMeshes = scene.getMeshMap();
        for (Mesh mesh : mapMeshes.keySet()) {
            sceneShader.setUniform("material", mesh.getMaterial());
            mesh.renderList(mapMeshes.get(mesh), (GameItem gameItem) -> {
                Matrix4f modelViewMatrix = transformation.buildModelViewMatrix(gameItem, viewMatrix);
                sceneShader.setUniform("modelViewMatrix", modelViewMatrix);
            }
            );
        }

        sceneShader.unbind();
    }

    private void renderLights(Matrix4f viewMatrix, SceneLight sceneLight) {

        sceneShader.setUniform("ambientLight", sceneLight.getAmbientLight());
        sceneShader.setUniform("specularPower", specularPower);

        // Process Point Lights
        PointLight[] pointLightList = sceneLight.getPointLights();
        int numLights = pointLightList != null ? pointLightList.length : 0;
        for (int i = 0; i < numLights; i++) {
            // Get a copy of the point light object and transform its position to view coordinates
            PointLight currPointLight = new PointLight(pointLightList[i]);
            Vector3f lightPos = currPointLight.getPosition();
            Vector4f aux = new Vector4f(lightPos, 1);
            aux.mul(viewMatrix);
            lightPos.x = aux.x;
            lightPos.y = aux.y;
            lightPos.z = aux.z;
            sceneShader.setUniform("pointLights", currPointLight, i);
        }

        // Process Spot Ligths
        SpotLight[] spotLightList = sceneLight.getSpotLights();
        numLights = spotLightList != null ? spotLightList.length : 0;
        for (int i = 0; i < numLights; i++) {
            // Get a copy of the spot light object and transform its position and cone direction to view coordinates
            SpotLight currSpotLight = new SpotLight(spotLightList[i]);
            Vector4f dir = new Vector4f(currSpotLight.getConeDirection(), 0);
            dir.mul(viewMatrix);
            currSpotLight.setConeDirection(new Vector3f(dir.x, dir.y, dir.z));

            Vector3f lightPos = currSpotLight.getPointLight().getPosition();
            Vector4f aux = new Vector4f(lightPos, 1);
            aux.mul(viewMatrix);
            lightPos.x = aux.x;
            lightPos.y = aux.y;
            lightPos.z = aux.z;

            sceneShader.setUniform("spotLights", currSpotLight, i);
        }

        // Get a copy of the directional light object and transform its position to view coordinates
        DirectionalLight currDirLight = new DirectionalLight(sceneLight.getDirectionalLight());
        Vector4f dir = new Vector4f(currDirLight.getDirection(), 0);
        dir.mul(viewMatrix);
        currDirLight.setDirection(new Vector3f(dir.x, dir.y, dir.z));
        sceneShader.setUniform("directionalLight", currDirLight);
    }


    public void cleanup() {
        if (skyBoxShader != null) {
            skyBoxShader.cleanup();
        }
        if (sceneShader != null) {
            sceneShader.cleanup();
        }
    }
}