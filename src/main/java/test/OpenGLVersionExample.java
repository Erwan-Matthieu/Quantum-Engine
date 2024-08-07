package test;

import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.*;

public class OpenGLVersionExample {
    public static void main(String[] args) {
        // Initialize the LWJGL OpenGL context
        long window = init();
        if (window != 0) {
            // Make the OpenGL context current
            GL.createCapabilities();

            // Get the OpenGL version
            String version = glGetString(GL_VERSION);
            System.out.println("OpenGL Version: " + version);

            // Cleanup and terminate the window
            cleanup(window);
        }
    }

    private static long init() {
        // Initialize GLFW
        if (!org.lwjgl.glfw.GLFW.glfwInit()) {
            System.err.println("Failed to initialize GLFW");
            return 0;
        }

        // Create a windowed mode window and its OpenGL context
        long window = org.lwjgl.glfw.GLFW.glfwCreateWindow(800, 600, "OpenGL Version Example", 0, 0);
        if (window == 0) {
            System.err.println("Failed to create GLFW window");
            org.lwjgl.glfw.GLFW.glfwTerminate();
            return 0;
        }

        // Make the window's context current
        org.lwjgl.glfw.GLFW.glfwMakeContextCurrent(window);

        // Enable v-sync
        org.lwjgl.glfw.GLFW.glfwSwapInterval(1);

        return window;
    }

    private static void cleanup(long window) {
        // Destroy the window and terminate GLFW
        org.lwjgl.glfw.GLFW.glfwDestroyWindow(window);
        org.lwjgl.glfw.GLFW.glfwTerminate();
    }
}
