package com.quantum.utils.logging;

import java.io.File;
import java.io.FilenameFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.quantum.utils.logging.Logging.getLogger;
import static com.quantum.utils.config.ConfigurationProperties.getProperty;

/**
 * Manages the cleanup of old log files based on a retention policy.
 * <p>
 * The {@link Cleanup} class extends {@link Thread} and is responsible for deleting log files
 * older than a specified number of days. The retention period is configured through the
 * {@code RETENTION_DAYS} property.
 * </p>
 * <p>
 * This class is typically run as a background thread to ensure that old log files are
 * periodically removed to free up disk space.
 * </p>
 *
 * <h2>Usage Example</h2>
 * <pre>
 * {@code
 * Cleanup cleanupThread = new Cleanup();
 * cleanupThread.start();
 * }
 * </pre>
 * 
 * @author RAZANAKOLONA Erwan-Matthieu Liantsoa
 * @version 1.0
 * @since 1.0
 * 
 * @see Logging
 */
public class Cleanup extends Thread {

    private static final Logger LOGGER = getLogger(Cleanup.class);

    private static final String LOGS_DIRECTORY = "logs";

    private static int RETENTION_DAYS = Integer.parseInt(getProperty("RETENTION_DAYS"));

    /**
     * Executes the cleanup process. This method is called when the thread is started.
     * <p>
     * It checks the logs directory for log files, determines which files are older than the
     * retention period, and deletes them. Logs the number of files deleted and any errors that
     * occur during the process.
     * </p>
     * 
     * @since 1.0
     */
    @Override
    public void run() {
        File logs = new File(LOGS_DIRECTORY);

        LOGGER.info("Open logs directory");

        if (!logs.exists()) {
            LOGGER.severe("logs directory doesn't exist");
        } else if (!logs.isDirectory()) {
            LOGGER.severe("logs isn't a directory");
            return;
        }

        File[] logFiles = logs.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".log");
            }
            
        });

        LOGGER.info("There are " + logFiles.length + " log files.");

        int deletedLogFilesCount = 0;

        if (logFiles != null) {
            Date cutOffDate = getCutOffDate();

            for (File file : logFiles) {
                try {
                    if (extract(file.getName()).getTime() < cutOffDate.getTime()) {
                        if (file.delete()) {
                            deletedLogFilesCount++;
                        } else {
                            LOGGER.warning("Failed to deletelog file: " + file.getName());
                        }
                    }
                } catch (ParseException e) {
                    LOGGER.log(Level.SEVERE, "An execption occured", e);
                }
            }
        }

        String fileMeasure = (deletedLogFilesCount > 1) ? "files" : "file";

        LOGGER.info("Number of log files deleted successfully: " + deletedLogFilesCount + " " + fileMeasure);

    }

    /**
     * Calculates the cutoff date for log file retention.
     * <p>
     * The cutoff date is determined by subtracting the retention period (in days) from the
     * current date. Files older than this cutoff date will be deleted.
     * </p>
     * 
     * @return The cutoff date.
     * 
     * @since 1.0
     */
    private static Date getCutOffDate() {
        Calendar calendar = new GregorianCalendar();
        LOGGER.info("Retention days set to " + RETENTION_DAYS + " days");
        calendar.add(Calendar.DAY_OF_MONTH, -RETENTION_DAYS);

        return calendar.getTime();
    }

    /**
     * Extracts the date from a log file name.
     * <p>
     * Assumes that the file name starts with a date in the format {@code yyyy-MM-dd}, followed
     * by additional characters. The method parses this date to determine the age of the log file.
     * </p>
     * 
     * @param filename The name of the log file.
     * @return The date extracted from the file name.
     * @throws ParseException If the date in the file name cannot be parsed.
     * 
     * @see 1.0
     */
    private static Date extract(String filename) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(filename.substring(3));
    }
 
}
