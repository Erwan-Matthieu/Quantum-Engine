package com.quantum.launcher;

import com.quantum.core.GameEngine;
import com.quantum.core.logic.IGameLogic;

public class Launcher {

    private final static String OS_NAME = System.getProperty("os.name");

    public static void main(String[] args) {
        try {
            boolean vSync = true;
            IGameLogic gameLogic = new MyGame();
            GameEngine gameEngine = new GameEngine("Game", vSync, gameLogic);
            
            if (OS_NAME.contains("Mac")) {
                gameEngine.run();
            } else {
                gameEngine.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
}