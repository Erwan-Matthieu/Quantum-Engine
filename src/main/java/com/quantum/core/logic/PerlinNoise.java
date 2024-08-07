package com.quantum.core.logic;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

import org.lwjgl.system.MemoryUtil;

public class PerlinNoise {

    private final int[] permutation,
                        p;

    private Random random;

    public PerlinNoise(long seed) {
        this.permutation = new int[256];
        this.p = new int[512];
        random = new Random(seed);
        initializePermutation();
    }

    private void initializePermutation() {
        for (int i = 0; i < 256; i++) {
            permutation[i] = i;
        }

        for (int i = 0; i < 256; i++) {
            int j = random.nextInt(256);
            int swap = permutation[i];
            permutation[i] = permutation[j];
            permutation[j] = swap;
        }

        for (int i = 0; i < 512; i++) {
            p[i] = permutation[i % 256];
        }
    }

    public double noise(double x, double y) {
        int xi = (int) Math.floor(x) & 255;
        int yi = (int) Math.floor(y) & 255;

        double xf = x - Math.floor(x);
        double yf = y - Math.floor(y);

        double u = fade(xf);
        double v = fade(yf);

        int aa = p[p[xi] + yi];
        int ab = p[p[xi] + yi + 1];
        int ba = p[p[xi + 1] + yi];
        int bb = p[p[xi + 1] + yi + 1];

        double x1, x2, y1;

        x1 = lerp(grad(aa, xf, yf), grad(ba, xf - 1, yf), u);
        x2 = lerp(grad(ab, xf, yf - 1), grad(bb, xf - 1, yf - 1), u);
        y1 = lerp(x1, x2, v);

        return y1;
    }

    private double lerp(double a, double b, double t) {
        return a + t * (b - a);
    }

    private double grad(int hash, double x, double y) {
        int h = hash & 15;
        double u = h < 8 ? x : y;
        double v = h < 4 ? y : h == 12 || h == 14 ? x : 0;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

    private double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    public ByteBuffer generate(int widht, int height) {
        float[][] noise = new float[widht][height];

        double scale = 100.;

        for (int i = 0; i < widht; i++) {
            for (int j = 0; j < height; j++) {
                noise[i][j] = (float) noise(i / scale, j / scale);
            }
        }

        ByteBuffer buffer = MemoryUtil.memAlloc(widht * height);
        buffer.order(ByteOrder.nativeOrder());

        for (int i = 0; i < widht; i++) {
            for (int j = 0; j < height; j++) {
                byte value = (byte) ((noise[i][j] + 1.0) * 0.5 * 255);
                buffer.put(value);
            }
        }

        buffer.flip();

        return buffer;
    }

    private static BufferedImage createImageFromBufferedImage(ByteBuffer buffer, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        buffer.get(data);
        return image;
    }

    public static void main(String[] args) {
        int widht = 256,
            height = 256;

        ByteBuffer buffer = new PerlinNoise(42).generate(widht, height);

        try {
            BufferedImage image = createImageFromBufferedImage(buffer, widht, height);
            ImageIO.write(image, "png", new File("output2.png"));
            System.out.println("Image saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
