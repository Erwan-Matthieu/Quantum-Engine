package com.quantum.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.quantum.core.graphics.Mesh;
import com.quantum.entity.SkyBox;
import com.quantum.entity.light.SceneLight;

public class Scene {
    
    private Map<Mesh, List<GameItem>> meshMap;

    private SkyBox skyBox;

    private SceneLight sceneLight;

    public Scene() {
        meshMap = new HashMap<>();
    }

    public SkyBox getSkyBox() {
        return skyBox;
    }

    public void setSkyBox(SkyBox skyBox) {
        this.skyBox = skyBox;
    }

    public SceneLight getSceneLight() {
        return sceneLight;
    }

    public void setSceneLight(SceneLight sceneLight) {
        this.sceneLight = sceneLight;
    }

    public Map<Mesh, List<GameItem>> getMeshMap() {
        return meshMap;
    }

    public void setMeshMap(GameItem[] gameItems) {
        int numGameItems = gameItems!= null ? gameItems.length : 0;

        for (int i = 0; i < numGameItems; i++) {
            GameItem gameItem = gameItems[i];

            Mesh mesh = gameItem.getMesh();

            List<GameItem>list = meshMap.get(mesh);

            if (list == null) {
                list = new ArrayList<>();
                meshMap.put(mesh, list);
            }

            list.add(gameItem);
        }
    }

}
