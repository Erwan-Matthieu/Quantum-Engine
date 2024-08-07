package test;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.MemoryHandler;

public class LoggingExample {
    public static void main(String[] args) {
        // Create a logger
        Logger logger = Logger.getLogger(LoggingExample.class.getName());
        
        // Create a ConsoleHandler to output logs to the console
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        
        // Create a MemoryHandler with a buffer size of 100 and a threshold of 50
        MemoryHandler memoryHandler = new MemoryHandler(consoleHandler, 50, Level.ALL);
        memoryHandler.setLevel(Level.ALL);
        
        // Add the MemoryHandler to the logger
        logger.addHandler(memoryHandler);
        
        // Log some messages
        for (int i = 0; i < 60; i++) {
            logger.info("Log message " + i);
        }
        
        // Flush the memory handler to ensure all buffered messages are output
        memoryHandler.flush();
    }
}
