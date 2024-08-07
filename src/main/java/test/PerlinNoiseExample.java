package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PerlinNoiseExample extends JPanel {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private BufferedImage noiseImage;

    public PerlinNoiseExample() {
        noiseImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        generatePerlinNoise();
    }

    private void generatePerlinNoise() {
        PerlinNoise noise = new PerlinNoise();
        double scale = 100.0;

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                double nx = x / scale;
                double ny = y / scale;
                double noiseValue = noise.noise(nx, ny);
                int colorValue = (int) ((noiseValue + 1) * 127.5);
                Color color = new Color(colorValue, colorValue, colorValue);
                noiseImage.setRGB(x, y, color.getRGB());
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(noiseImage, 0, 0, this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Perlin Noise Example");
        PerlinNoiseExample panel = new PerlinNoiseExample();
        frame.add(panel);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class PerlinNoise {
    private final int[] permutation;
    private final int[] p;

    public PerlinNoise() {
        this.permutation = new int[256];
        this.p = new int[512];
        initializePermutation();
    }

    private void initializePermutation() {
        for (int i = 0; i < 256; i++) {
            permutation[i] = i;
        }

        for (int i = 0; i < 256; i++) {
            int j = (int) (Math.random() * 256);
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

    private double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
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
}
