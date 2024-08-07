package com.quantum.utils.logging;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class HardwareLogging {
    
    private static class QEHardwareLoggingFileHandler extends FileHandler {
    
        private static final String DATE_FORMAT = "yyyy-MM-dd";
        private static final String FILE_NAME = "logs/QE-HI-%s.log";
        private static final Object lock = new Object();
        private static String currFileName;

        private static String generateFileName() {
            String date = new SimpleDateFormat(DATE_FORMAT).format(new Date());
            return String.format(FILE_NAME, date);
        }

        public QEHardwareLoggingFileHandler() throws SecurityException, IOException {
            super(generateFileName(), true);
            setFormatter(new QEHardwareFormatter());
        }

        @Override
        public void publish(LogRecord record) {
            synchronized(lock) {
                try {
                    if (this.shouldRollOver()) {
                        this.close();
                        currFileName = generateFileName();
                        this.setOutputStream(new FileOutputStream(currFileName, true));
                    }
                    super.publish(record);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private boolean shouldRollOver() {
            String newFileName = generateFileName();
            return !newFileName.equals(currFileName);
        }

    }

    private static class QEHardwareFormatter extends Formatter {

        private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.US);

        @Override
        public String format(LogRecord record) {
            String timestamp = DATE_FORMAT.format(new Date(record.getMillis()));

            String className = record.getSourceClassName();
            String methodName = record.getSourceMethodName();

            StringBuilder builder = new StringBuilder(String.format("[%s] %s %s\n \t%s:%s%n", 
                                                                    timestamp, className, methodName, 
                                                                    record.getLevel(), record.getMessage()));
            
            if (record.getThrown() != null) {
                builder .append("\t\tException: ")
                        .append(record.getThrown().toString())
                        .append("\n");

                for (StackTraceElement stackTraceElement : record.getThrown().getStackTrace()) {
                    builder .append("\t\t\t")
                            .append(stackTraceElement.toString())
                            .append("\n");
                }
            }

            return builder.toString();

        }
    }

    private volatile static Logger logger;

    public static Logger getLogger(Class<?> clazz) {
        if (logger == null) {
            synchronized(Logging.class) {
                if (logger == null) {
                    logger = Logger.getLogger(clazz.getName());

                    try {
                        logger.addHandler(new QEHardwareLoggingFileHandler());
                        logger.setLevel(Level.ALL);
                    } catch (SecurityException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return logger;
    }

}
