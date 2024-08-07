package com.quantum.core.io;

import static com.quantum.utils.logging.Logging.getLogger;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.logging.Logger;
import java.util.logging.Level;

import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryUtil;

import com.quantum.system.CPUMonitor;
import com.quantum.system.GPUMonitor;
import com.quantum.ui.event.QEKeyListener;

public class Window {

    private static final Logger LOGGER = getLogger(Window.class);

    private int width,
                height;

    private long windowHandle;
    
    private String title;
    
    private boolean resized,
                    vSync;

    public int frames;

    public long times;

    public Window(int width, int height, String windowTitle, boolean vSync) {
        this.width = width;
        this.height = height;
        this.title = windowTitle;
        this.resized = false;
        this.vSync = vSync;
    }

    public void create() {

        glfwSetErrorCallback(new GLFWErrorCallbackI() {

            @Override
            public void invoke(int error, long description) {
                String errorMessage = MemoryUtil.memUTF8(description);

                LOGGER.log(Level.SEVERE, "An exception occurred", errorMessage);
            }
            
        });

        if (!glfwInit()) {
            IllegalStateException exception = new IllegalStateException("Unable to initialize GLFW");
            LOGGER.log(Level.SEVERE, "An exception occurred", exception);
            throw exception;
        }

        glfwDefaultWindowHints();

        {
            glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
            glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6);
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        }

        windowHandle = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        if (windowHandle == NULL) {
            RuntimeException exception = new RuntimeException("Failed to create the GLFW window");
            LOGGER.log(Level.SEVERE, "An exception occurred", exception);
            throw exception;
        }

        glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> {
            this.width = width;
            this.height = height;
            this.setResized(true);
        });

        glfwSetKeyCallback(windowHandle, new QEKeyListener() {

            @Override
            public void keyPressed(long window, int key, int scancode, int mods) {
                if (key == GLFW_KEY_ESCAPE) {
                    glfwSetWindowShouldClose(window, true);
                }
            }

        });

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        glfwSetWindowPos(
            windowHandle, 
            (vidMode.width() - this.width) / 2, 
            (vidMode.height() - this.height) / 2
        );

        glfwMakeContextCurrent(windowHandle);
        createCapabilities();

        int errorCode = glGetError();
        if (errorCode != GL_NO_ERROR) {
            LOGGER.log(Level.SEVERE, "OpenGL Error after creating capabilities: " + errorCode);
        } else {
            LOGGER.info("OpenGL capabilities created successfully.");
        }

        if (isvSync()) {
            glfwSwapInterval(1);
        }

        new CPUMonitor().log();
        new GPUMonitor().log();

        glfwShowWindow(windowHandle);

        glClearColor(0.f, 0.f, 0.f, 0.0f);

        glEnable(GL_DEPTH_TEST);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        // glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
    }

    public void setClearColor(float red, float green, float blue, float alpha) {
        glClearColor(red, green, blue, alpha);
    }

    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
    }

    public boolean windowShouldClose() {
        return glfwWindowShouldClose(windowHandle);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public boolean isResized() {
        return resized;
    }

    public void setResized(boolean resized) {
        this.resized = resized;
    }

    public boolean isvSync() {
        return vSync;
    }

    public void setvSync(boolean vSync) {
        this.vSync = vSync;
    }

    public void update() {
        glfwSwapBuffers(windowHandle);
        glfwPollEvents();
    }

    public long getWindowHandler() {
        return this.windowHandle;
    }

    
}
