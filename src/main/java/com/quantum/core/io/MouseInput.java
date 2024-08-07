package com.quantum.core.io;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;

import org.joml.Vector2d;
import org.joml.Vector2f;

import com.quantum.ui.event.QEMouseActionListener;
import com.quantum.ui.event.QEMouseEvent;
import com.quantum.ui.event.QEMouseMotionListener;
import com.quantum.ui.event.QEMouseWheelListener;

public class MouseInput {

    private final Vector2d  previousPos,
                            currentPos;

    private final Vector2f displVec;

    private boolean inWindow = false;
    private boolean leftButtonPressed = false;
    private boolean rightButtonPressed = false;

    public MouseInput() {
        previousPos = new Vector2d(-1, -1);
        currentPos = new Vector2d(0, 0);
        displVec = new Vector2f();
    }

    public void init(Window window) {
        glfwSetCursorPosCallback(window.getWindowHandler(), new QEMouseMotionListener() {

            @Override
            public void isEntered(QEMouseEvent e) {
                inWindow = true;
            }

            @Override
            public void mouseMoved(QEMouseEvent e) {
                currentPos.x = e.getX();
                currentPos.y = e.getY();
            }

        });

        glfwSetScrollCallback(window.getWindowHandler(), new QEMouseWheelListener() {

            @Override
            public void mouseWheelMoved(QEMouseEvent e) {
                System.out.println(e.getxOffset());
                System.out.println(e.getyOffset());
            }
            
        });

        glfwSetMouseButtonCallback(window.getWindowHandler(), new QEMouseActionListener() {

            @Override
            public void isPressed(QEMouseEvent e) {
                if (e.getButton() == GLFW_MOUSE_BUTTON_1) {
                    rightButtonPressed = true;
                }
            }

            @Override
            public void isReleased(QEMouseEvent e) {
                rightButtonPressed = false;
            }

            @Override
            public void isClicked(QEMouseEvent qeMouseEvent) {
                System.out.println("Test");
            }

        });

    }

    public void input(Window window) {
        displVec.x = 0;
        displVec.y = 0;
        if (previousPos.x > 0 && previousPos.y > 0 && inWindow) {
            double deltax = currentPos.x - previousPos.x;
            double deltay = currentPos.y - previousPos.y;
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;
            if (rotateX) {
                displVec.y = (float) deltax;
            }
            if (rotateY) {
                displVec.x = (float) deltay;
            }
        }
        previousPos.x = currentPos.x;
        previousPos.y = currentPos.y;
    }

    public Vector2d getCurrentPos() {
        return currentPos;
    }

    public Vector2f getDisplVec() {
        return displVec;
    }

    public boolean isInWindow() {
        return inWindow;
    }

    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }

    

}
