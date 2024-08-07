package com.quantum.utils;

import static com.quantum.utils.FileUtils.readAllLines;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.quantum.core.graphics.Mesh;

/**
 * A utility class for loading 3D models from OBJ files.
 * <p>
 * The {@code OBJLoader} class provides functionality to parse OBJ files, which are a common format
 * for representing 3D models, and convert their data into a {@link Mesh} object used for rendering.
 * </p>
 * 
 * @author RAZANAKOLONA Erwan-Matthieu Liantsoa
 * @version 1.0
 * @since 1.0
 * 
 * @see FileUtils
 * @see Mesh
 */
public class OBJLoader {

    /**
     * Loads a 3D mesh from the specified OBJ file.
     * <p>
     * This method reads the OBJ file, parses its contents, and constructs a {@link Mesh} object
     * containing vertex positions, texture coordinates, normals, and face indices.
     * </p>
     * 
     * @param fileName the path to the OBJ file on the classpath
     * @return the {@link Mesh} object created from the OBJ file data
     * @throws Exception if an error occurs while reading the file or parsing its contents
     * 
     * @since 1.0
     */
    public static Mesh loadMesh(String fileName) throws Exception {
        List<String> lines = readAllLines(fileName);

        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Face> faces = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.split("\\s+");

            switch (tokens[0]) {
                case "v" -> {
                    Vector3f vec3f = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]));

                    vertices.add(vec3f);
                }

                case "vt" -> {
                    Vector2f vec2f = new Vector2f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]));

                    textures.add(vec2f);
                }

                case "vn" -> {
                    Vector3f vec3f = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]));

                    normals.add(vec3f);
                }

                case "f" -> {
                    Face face = new Face(tokens[1], tokens[2], tokens[3]);
                    faces.add(face);
                }

                default -> {
                }
            }
        }

        return reorderLists(vertices, textures, normals, faces);
    }

    /**
     * Reorders the vertex, texture coordinate, and normal lists based on face indices
     * and constructs a {@link Mesh} object.
     * <p>
     * This method processes the faces to correctly arrange the vertex attributes and indices
     * for the mesh, ensuring that the mesh data is correctly formatted for rendering.
     * </p>
     * 
     * @param posList the list of vertex positions
     * @param textCoordList the list of texture coordinates
     * @param normList the list of normals
     * @param facesList the list of faces containing vertex indices
     * @return the constructed {@link Mesh} object
     * 
     * @since 1.0
     */
    private static Mesh reorderLists(List<Vector3f> posList, List<Vector2f> textCoordList, List<Vector3f> normList,
            List<Face> facesList) {

        List<Integer> indices = new ArrayList<>();

        float[] posArr = new float[posList.size() * 3];
        int i = 0;
        for (Vector3f pos : posList) {
            posArr[i * 3] = pos.x;
            posArr[i * 3 + 1] = pos.y;
            posArr[i * 3 + 2] = pos.z;
            i++;
        }
        float[] textCoordArr = new float[posList.size() * 2];
        float[] normArr = new float[posList.size() * 3];

        for (Face face : facesList) {
            IdxGroup[] faceVertexIndices = face.getFaceVertexIndices();
            for (IdxGroup indValue : faceVertexIndices) {
                processFaceVertex(indValue, textCoordList, normList, indices, textCoordArr, normArr);
            }
        }
        int[] indicesArr = new int[indices.size()];

        indicesArr = indices.stream().mapToInt((Integer v) -> v).toArray();
        Mesh mesh = new Mesh(posArr, textCoordArr, normArr, indicesArr);
        return mesh;

    }

    /**
     * Processes the vertex indices for a face and updates the vertex attributes arrays.
     * <p>
     * This method assigns texture coordinates and normals to the appropriate positions in
     * the arrays based on the face vertex indices.
     * </p>
     * 
     * @param indices the indices for the face vertex
     * @param textCoordList the list of texture coordinates
     * @param normList the list of normals
     * @param indicesList the list to store vertex indices
     * @param textCoordArr the array to store texture coordinates
     * @param normArr the array to store normals
     * 
     * @since 1.0
     */
    private static void processFaceVertex(IdxGroup indices, List<Vector2f> textCoordList, List<Vector3f> normList,
            List<Integer> indicesList, float[] textCoordArr, float[] normArr) {

        int posIndex = indices.idxPos;
        indicesList.add(posIndex);

        if (indices.idxTextCoords >= 0) {
            Vector2f textCoord = textCoordList.get(indices.idxTextCoords);
            textCoordArr[posIndex * 2] = textCoord.x;
            textCoordArr[posIndex * 2 + 1] = 1 - textCoord.y;
        }
        if (indices.idxVecNormal >= 0) {
            // Reorder vectornormals
            Vector3f vecNorm = normList.get(indices.idxVecNormal);
            normArr[posIndex * 3] = vecNorm.x;
            normArr[posIndex * 3 + 1] = vecNorm.y;
            normArr[posIndex * 3 + 2] = vecNorm.z;
        }
    }

    /**
     * A helper class representing a face in the OBJ file.
     * <p>
     * The {@code Face} class stores the vertex indices for a face and provides methods
     * to parse and access the indices.
     * </p>
     * 
     * @version 1.0
     * @since 1.0
     */
    private static class Face {

        private IdxGroup[] idxGroups = new IdxGroup[3];

        /**
         * Constructs a {@code Face} object with the specified vertex indices.
         * 
         * @param v1 the first vertex index
         * @param v2 the second vertex index
         * @param v3 the third vertex index
         * 
         * @since 1.0
         */
        public Face(String v1, String v2, String v3) {
            idxGroups[0] = parseLine(v1);
            idxGroups[1] = parseLine(v2);
            idxGroups[2] = parseLine(v3);
        }

        private IdxGroup parseLine(String line) {
            IdxGroup idxGroup = new IdxGroup();

            String[] lineTokens = line.split("/");

            int length = lineTokens.length;
            idxGroup.idxPos = Integer.parseInt(lineTokens[0]) - 1;
            if (length > 1) {
                String textCoord = lineTokens[1];
                idxGroup.idxTextCoords = textCoord.length() > 0 ? Integer.parseInt(textCoord) - 1 : IdxGroup.NO_VALUE;
                if (length > 2) {
                    idxGroup.idxVecNormal = Integer.parseInt(lineTokens[2]) - 1;
                }
            }

            return idxGroup;
        }

        /**
         * Returns the vertex indices for the face.
         * 
         * @return an array of {@link IdxGroup} representing the face vertex indices
         * 
         * @version 1.0
         */
        public IdxGroup[] getFaceVertexIndices() {
            return idxGroups;
        }

    }

    private static class IdxGroup {

        public static final int NO_VALUE = -1;

        @SuppressWarnings("unused")
        public int idxPos,
                idxTextCoords,
                idxVecNormal;

        public IdxGroup() {
            idxPos = idxTextCoords = idxVecNormal = NO_VALUE;
        }

    }

}
