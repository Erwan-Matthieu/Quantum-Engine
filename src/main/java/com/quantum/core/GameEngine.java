package com.quantum.core;

import static com.quantum.utils.logging.Logging.getLogger;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.quantum.core.io.MouseInput;
import com.quantum.core.io.Window;
import com.quantum.core.logic.IGameLogic;
import com.quantum.core.logic.Timer;
import com.quantum.system.CPUMonitor;
import com.quantum.system.MemoryMonitor;
import com.quantum.utils.logging.Cleanup;

public class GameEngine implements Runnable {

    private static final Logger LOGGER = getLogger(GameEngine.class);

    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private static final int    WIDTH = (int) (screenSize.getWidth() * 0.6),
                                HEIGHT = (int) (screenSize.getHeight() * 0.7),
                                TARGET_FPS = 75,
                                TARGET_UPS = 30;

    private final Thread gameLoopThread;
    
    private final Window window;
    
    private final Timer timer;

    private IGameLogic gameLogic;

    private MouseInput mouseInput;

    private Cleanup cleanUp = new Cleanup();

    private CPUMonitor CPUMonitor = new CPUMonitor();

    private MemoryMonitor memoryMonitor = new MemoryMonitor();

    public GameEngine(String windowTitle, boolean vSync, IGameLogic gameLogic) throws Exception {
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        cleanUp.start();
        CPUMonitor.start();
        memoryMonitor.start();
        window = new Window(WIDTH, HEIGHT, windowTitle, vSync);
        mouseInput = new MouseInput();
        this.gameLogic = gameLogic;
        timer = new Timer();
    }

    public void start() {
        gameLoopThread.start();
    }

    @Override
    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    protected void input() {
        mouseInput.input(window);
        gameLogic.input(window, mouseInput);
    }

    protected void update(float interval) {
        gameLogic.update(interval, mouseInput);
    }

    protected void render() {
        gameLogic.render(window);
        window.update();
    }

    private void gameLoop() {
        try {
            float elapsedTime,
                    accumulator = 0f,
                    interval = 1f / TARGET_UPS;

            boolean running = true;

            while (running && !window.windowShouldClose()) {
                elapsedTime = timer.getElapsedTime();
                accumulator += elapsedTime;

                input();

                while (accumulator >= interval) {
                    update(interval);
                    accumulator -= interval;
                }

                render();

                if (!window.isvSync()) {
                    sync();
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An exception occurred", e);
        }

        CPUMonitor.interrupt();
        memoryMonitor.interrupt();

    }

    public void cleanup() {
        gameLogic.cleanup();
    }

    private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;

        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
    }

    private void init() throws Exception {
        window.create();
        timer.init();
        mouseInput.init(window);
        gameLogic.init(window);
    }

}
