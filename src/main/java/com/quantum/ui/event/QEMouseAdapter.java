package com.quantum.ui.event;

public interface QEMouseAdapter {

    void mouseMoved(QEMouseEvent e);

    void isExited(QEMouseEvent e);

    void isEntered(QEMouseEvent e);

    void mouseWheelMoved(QEMouseEvent e);

    void isReleased(QEMouseEvent e);

    void isPressed(QEMouseEvent e);

}