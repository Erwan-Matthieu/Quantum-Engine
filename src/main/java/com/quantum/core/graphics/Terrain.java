package com.quantum.core.graphics;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

import com.quantum.core.GameItem;

import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_image_free;

public class Terrain {

    private final GameItem[] gameItems;

    private final Box2D[][] boundingBoxes;

    private final int terrainSize;

    private float verticesPerCol;

    private float verticesPerRow;

    private HeightMapMesh heightMapMesh;

    public Terrain(int terrainSize, float scale, float minY, float maxX, String heightMap,String textureFile, int textInc) throws Exception {
        this.terrainSize = terrainSize;
        gameItems = new GameItem[terrainSize * terrainSize];

        ByteBuffer buf = null;
        int width;
        int height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            buf = stbi_load(heightMap, w, h, channels, 4);
            if (buf == null) {
                throw new Exception("Image file [" + heightMap  + "] not loaded: " + stbi_failure_reason());
            }

            width = w.get();
            height = h.get();
        }

        verticesPerRow = height - 1;
        verticesPerCol = width - 1;

        this.heightMapMesh = new HeightMapMesh(minY, maxX, buf, width, height, textureFile,textInc);

        boundingBoxes = new Box2D[terrainSize][terrainSize];

        for (int row = 0; row < terrainSize; row++) {
            for (int col = 0; col < terrainSize; col++) {
                float xDisplacement = (col - ((float) terrainSize - 1) / (float) 2) * scale * HeightMapMesh.getXLength();
                float zDisplacement = (row - ((float) terrainSize - 1) / (float) 2) * scale * HeightMapMesh.getZLength();

                GameItem terrainBlock = new GameItem(heightMapMesh.getMesh());
                terrainBlock.setScale(scale);
                terrainBlock.setPosition(xDisplacement, 0, zDisplacement);
                gameItems[row * terrainSize + col] = terrainBlock;

                boundingBoxes[row][col] = getBoundingBox(terrainBlock);
            }
        }

        stbi_image_free(buf);
     
    }

    public float getHeight(Vector3f position) {
        float result = Float.MIN_VALUE;

        Box2D boundingBox = null;

        boolean found = false;

        GameItem terrainBlock = null;

        for (int row = 0; row < terrainSize && !found; row++) {
            for (int col = 0; col < terrainSize && !found; col++) {
                terrainBlock = gameItems[row * terrainSize + col];
                boundingBox = boundingBoxes[row][col];
                found = boundingBox.contains(position.x, position.z);
            }
        }

        if (found) {
            Vector3f[] triangle = getTriangle(position, boundingBox, terrainBlock);
            result = interpolateHeight(triangle[0], triangle[1], triangle[2], position.x, position.z);
        }

        return result;
    }

    private float interpolateHeight(Vector3f pA, Vector3f pB, Vector3f pC, float x, float z) {
        // Plane equation ax+by+cz+d=0
        float a = (pB.y - pA.y) * (pC.z - pA.z) - (pC.y - pA.y) * (pB.z - pA.z);
        float b = (pB.z - pA.z) * (pC.x - pA.x) - (pC.z - pA.z) * (pB.x - pA.x);
        float c = (pB.x - pA.x) * (pC.y - pA.y) - (pC.x - pA.x) * (pB.y - pA.y);
        float d = -(a * pA.x + b * pA.y + c * pA.z);
        // y = (-d -ax -cz) / b
        float y = (-d - a * x - c * z) / b;
        return y;
    }

    private Vector3f[] getTriangle(Vector3f position, Box2D boundingBox, GameItem terrainBlock) {
        float cellWidth = boundingBox.width/ (float) verticesPerCol;
        float cellHeight = boundingBox.height / (float) verticesPerRow;

        int col = (int) ((position.x - boundingBox.x) / cellWidth);
        int row = (int) ((position.z - boundingBox.y) / cellHeight);

        Vector3f[] triangle = new Vector3f[3];

        triangle[1] = new Vector3f(
                boundingBox.x + col * cellWidth,
                getWorldHeight(row + 1, col, terrainBlock),
                boundingBox.y + (row + 1) * cellHeight
        );

        triangle[2] = new Vector3f(
            boundingBox.x + (col + 1) * cellWidth,
            getWorldHeight(row, col + 1, terrainBlock),
            boundingBox.y + row * cellHeight
        );

        if (position.z < getDiagonalZCoord(triangle[1].x, triangle[1].z, triangle[2].x, triangle[2].z, position.x)) {
            triangle[0] = new Vector3f(
                    boundingBox.x + col * cellWidth,
                    getWorldHeight(row, col, terrainBlock),
                    boundingBox.y + row * cellHeight
            );
        } else {
            triangle[0] = new Vector3f(
                    boundingBox.x + (col + 1) * cellWidth,
                    getWorldHeight(row + 2, col + 1, terrainBlock),
                    boundingBox.y + (row + 1) * cellHeight
            );
        }

        return triangle;
    }


    private float getDiagonalZCoord(float x1, float z1, float x2, float z2, float x) {
        float z = ((z1 - z2) / (x1 - x2)) * (x - x1) + z1;
        return z;
    }

    private float getWorldHeight(int row, int col, GameItem gameItem) {
        float y = heightMapMesh.getHeight(row, col);
        return y * gameItem.getScale() + gameItem.getPosition().y;
    }

    public GameItem[] getGameItems() {
        return gameItems;
    }

    private Box2D getBoundingBox(GameItem terrainBlock) {
        float scale = terrainBlock.getScale();
        Vector3f position = terrainBlock.getPosition();

        float topLeftX = HeightMapMesh.START_X * scale + position.x;
        float topLeftZ = HeightMapMesh.START_Z * scale + position.z;
        float width = Math.abs(HeightMapMesh.START_X * 2) * scale;
        float height = Math.abs(HeightMapMesh.START_Z * 2) * scale;

        return new Box2D(topLeftX, topLeftZ, width, height);
    }

    static class Box2D {
        
        float   x,
                y;

        float   width,
                height;

        public Box2D(float x, float y, float width, float height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public boolean contains(float x, float y) {
            return  x >= this.x
                    && y >= this.y
                    && x <= this.x +this.width
                    && y <= this.y + this.height;
        }
        
    }

}
