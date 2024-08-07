package com.quantum.ui;

import java.awt.Point;

import com.quantum.utils.Color;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

public class QEButton {

    private int x,
                y,
                width,
                height;

    private String text;

    private Color   color = new Color(0.7f),
                    overColour = new Color(1f);

    private boolean mouseOverEnabled = false,
                    visible = true;

    public QEButton(int x, int y, int width, int height, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    public QEButton(Point point, int width, int height, String text) {
        this(point.x, point.y, width, height, text);
    }

    public void render() {

        if (visible) {
            if (isMouseOver() && mouseOverEnabled) {
                glColor3d(overColour.getRed(), overColour.getGreen(), overColour.getBlue());
            } else {
                glColor3d(color.getRed(), color.getGreen(), color.getBlue());
            }

            glBegin(GL_QUADS);

            {
                glVertex2d(x-1, y+1);
                glVertex2d(x+width+1, y+1);
                glVertex2d(x+width+1, y+height-1);
                glVertex2d(x-1, y+height-1);
            }

            glEnd();

            glBegin(GL_QUADS);

            {
                glVertex2d(x+1, y-1);
                glVertex2d(x+width-1, y-1);
                glVertex2d(x+width-1, y+height+1);
                glVertex2d(x+1, y+height+1);
            }

            glEnd();
        }
        
    }

    private boolean isMouseOver() {
        return true;
    }

}