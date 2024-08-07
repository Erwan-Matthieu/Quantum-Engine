package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadJarFile {
    public static void main(String[] args) {
        try {
            // Create a ProcessBuilder for the 'lscpu' command
            ProcessBuilder processBuilder = new ProcessBuilder("lscpu");
            Process process = processBuilder.start();
            
            // Read the output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Model name:")) {
                    System.out.println(line);
                }
            }
            reader.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("lspci", "-vnn", "|", "grep", "-i", "vga");
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("GPU: " + line.trim());
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        String filePath = "/etc/os-release";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("NAME=") || line.startsWith("VERSION_ID=")) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
