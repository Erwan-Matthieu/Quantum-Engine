package com.quantum.core.logic;

import com.quantum.core.io.MouseInput;
import com.quantum.core.io.Window;

public interface IGameLogic {
    
    void init(Window window) throws Exception;

    void input(Window window, MouseInput mouseInput);

    void update(float interval, MouseInput mouseInput);

    void render(Window window);

    void cleanup();

}
