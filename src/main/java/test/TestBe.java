package test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

public class TestBe {

    // Example function to generate Perlin noise (placeholder implementation)
    private static float[][] generatePerlinNoise(int width, int height) {
        float[][] noise = new float[width][height];
        Random rand = new Random();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                noise[x][y] = rand.nextFloat(); // Replace this with actual Perlin noise function
            }
        }
        return noise;
    }

    private static ByteBuffer perlinNoiseToByteBuffer(float[][] noise) {
        int width = noise.length;
        int height = noise[0].length;
        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height);
        buffer.order(ByteOrder.nativeOrder());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Convert noise value to byte (0-255)
                byte value = (byte) ((noise[x][y] + 1.0) * 0.5 * 255);
                buffer.put(value);
            }
        }
        buffer.flip(); // Prepare the buffer for reading
        return buffer;
    }

    public static void main(String[] args) {
        int width = 256;
        int height = 256;
        float[][] noise = generatePerlinNoise(width, height);
        ByteBuffer buffer = perlinNoiseToByteBuffer(noise);

        // Now you can use the ByteBuffer as needed
        // Example: output the buffer contents
        while (buffer.hasRemaining()) {
            System.out.print((buffer.get() & 0xFF) + " ");
        }
    }
}
