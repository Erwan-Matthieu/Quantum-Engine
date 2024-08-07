package com.quantum.utils;

import static com.quantum.utils.logging.Logging.getLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for file operations.
 * <p>
 * The {@code FileUtils} class provides methods to read file contents from the classpath and return them
 * as strings or lists of strings. It is designed to simplify reading file data and handling potential I/O
 * exceptions that may occur during the file operations.
 * </p>
 * 
 * @author RAZANAKOLONA Erwan-Matthieu Liantsoa
 * @version 1.0
 * @since 1.0
 */
public class FileUtils {

    private static final Logger LOGGER = getLogger(FileUtils.class);
    
    /**
     * Reads the content of a file located at the specified path and returns it as a single {@code String}.
     * <p>
     * The file is read from the classpath. Each line of the file is appended to the result with newline characters.
     * </p>
     * 
     * @param path the path to the file on the classpath
     * @return the content of the file as a {@code String}
     * 
     * @since 1.0
     */
    public static String loadAsString(String path) {
        StringBuilder result = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(FileUtils.class.getResourceAsStream(path)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result  .append(line)
                        .append("\n");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Couldn't find the file at " + path, e);
        }

        return result.toString();
    }

    /**
     * Reads all lines from a file located at the specified file name and returns them as a {@code List<String>}.
     * <p>
     * The file is read from the classpath. Each line of the file is added to the list.
     * </p>
     * 
     * @param fileName the name of the file on the classpath
     * @return a {@code List<String>} containing all lines from the file
     * 
     * @since 1.0
     */
    public static List<String> readAllLines(String fileName) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(FileUtils.class.getResourceAsStream(fileName)))) {
            String line;

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An exception occured", e);
        }

        return lines;
    }

}
