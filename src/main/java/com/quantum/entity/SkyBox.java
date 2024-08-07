package com.quantum.entity;

import static com.quantum.utils.logging.Logging.getLogger;
import static com.quantum.utils.OBJLoader.loadMesh;

import java.util.logging.Logger;

import com.quantum.core.GameItem;
import com.quantum.core.graphics.Mesh;
import com.quantum.core.graphics.Texture;

public class SkyBox extends GameItem {

    private static final Logger LOGGER = getLogger(SkyBox.class);

    public SkyBox(String objModel, String textureFile) throws Exception {
        super();
        Mesh skyBoxMesh = loadMesh(objModel);
        Texture skyBoxTexture = new Texture(textureFile);
        skyBoxMesh.setMaterial(new Material(0.0f, skyBoxTexture));
        setMesh(skyBoxMesh);
        setPosition(0, 0, 0);
    }
    
}
