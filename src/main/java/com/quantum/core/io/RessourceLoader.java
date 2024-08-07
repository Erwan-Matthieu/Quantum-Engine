package com.quantum.core.io;

import static com.quantum.utils.logging.Logging.getLogger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RessourceLoader {
    
    private static final Logger LOGGER = getLogger(RessourceLoader.class);

    public static String loadResource(String filePath) throws IOException {
        
        String result;


        try (   InputStream in = RessourceLoader.class.getResourceAsStream(filePath);
                Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name())) {
                result = scanner.useDelimiter("\\A").next();
            
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading file [" + filePath + "]", e);
            throw e;
        }

        return result;
        
    }

}
