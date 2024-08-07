package com.quantum.utils.logging;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.Handler;

/**
 * Provides a centralized logging utility with support for logging to both files and standard output.
 * <p>
 * The {@code Logging} class includes custom handlers for managing log files, as well as for printing
 * logs to {@link PrintStream} or {@link PrintWriter}. The log files are named with a date suffix and
 * are rolled over based on the date. Log messages are formatted with timestamps, class names, method
 * names, log levels, and exceptions if any.
 * </p>
 * <p>
 * Use {@link #getLogger(Class)} to obtain a logger instance that logs to files, or use
 * {@link #getLogger(Class, PrintWriter)} or {@link #getLogger(Class, PrintStream)} to log to
 * standard output or error streams.
 * </p>
 * 
 * @author RAZANAKOLONA Erwan-Matthieu Liantsoa
 * @version 1.0
 * @since 1.0
 */
public class Logging {

    /**
     * Custom {@link FileHandler} for managing log files with daily rolling and custom formatting.
     * 
     * @version 1.0
     * @since 1.0
     */
    private static class LogFileHandler extends FileHandler {
    
        private static final String DATE_FORMAT = "yyyy-MM-dd";
        private static final String FILE_NAME = "logs/QE-%s.log";
        private static final Object lock = new Object();
        private static String currFileName;

        /**
         * Generates the current log file name based on the date.
         *
         * @return The generated file name.
         * 
         * @since 1.0
         */
        private static String generateFileName() {
            String date = new SimpleDateFormat(DATE_FORMAT).format(new Date());
            return String.format(FILE_NAME, date);
        }

        /**
         * Creates a {@code LogFileHandler} with daily rolling and custom formatting.
         *
         * @throws SecurityException if a security manager exists and it denies access to the file.
         * @throws IOException if an I/O error occurs.
         * 
         * @since 1.0
         */
        public LogFileHandler() throws SecurityException, IOException {
            super(generateFileName(), true);
            setFormatter(new QELoggingFormat());
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

        /**
         * Determines whether the log file needs to be rolled over.
         *
         * @return {@code true} if the log file needs to be rolled over; {@code false} otherwise.
         * 
         * @since 1.0
         */
        private boolean shouldRollOver() {
            String newFileName = generateFileName();
            return !newFileName.equals(currFileName);
        }
        
    }

    /**
     * Custom {@link Formatter} for formatting log messages with timestamps, class names, method names,
     * log levels, and exception details.
     * 
     * @since 1.0
     */
    private static class QELoggingFormat extends Formatter{
        
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

    /**
     * Retrieves a {@link Logger} instance for the specified class that logs to files.
     *
     * @param clazz the class for which the logger is to be retrieved.
     * @return the logger instance.
     * 
     * @since 1.0
     */
    public static Logger getLogger(Class<?> clazz) {
        if (logger == null) {
            synchronized(Logging.class) {
                if (logger == null) {
                    logger = Logger.getLogger(clazz.getName());

                    try {
                        logger.addHandler(new LogFileHandler());
                        logger.setLevel(Level.ALL);
                    } catch (SecurityException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return logger;
    }

    /**
     * Retrieves a {@link Logger} instance for the specified class that logs to both files and a
     * {@link PrintWriter}.
     *
     * @param clazz the class for which the logger is to be retrieved.
     * @param writer the {@link PrintWriter} to which logs should be written.
     * @return the logger instance.
     * 
     * @since 1.0
     */
    public static Logger getLogger(Class<?> clazz, PrintWriter writer) {
        if (logger == null) {
            synchronized(Logging.class) {
                if (logger == null) {
                    logger = Logger.getLogger(clazz.getName());

                    try {
                        logger.addHandler(new LogFileHandler());
                        logger.setLevel(Level.ALL);

                        PrintWriterHandler writerHandler = new PrintWriterHandler(writer);
                        writerHandler.setFormatter(new QELoggingFormat());
                        
                        logger.addHandler(writerHandler);
                    } catch (SecurityException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return logger;
    }

    /**
     * Retrieves a {@link Logger} instance for the specified class that logs to both files and a
     * {@link PrintStream}.
     *
     * @param clazz the class for which the logger is to be retrieved.
     * @param printStream the {@link PrintStream} to which logs should be written.
     * @return the logger instance.
     * 
     * @since 1.0
     */
    public static Logger getLogger(Class<?> clazz, PrintStream printStream) {
        if (logger == null) {
            synchronized(Logging.class) {
                if (logger == null) {
                    logger = Logger.getLogger(clazz.getName());

                    try {
                        logger.addHandler(new LogFileHandler());
                        logger.setLevel(Level.ALL);

                        PrintStreamHandler streamHandler = new PrintStreamHandler(printStream);
                        
                        streamHandler.setFormatter(new QELoggingFormat());
                        
                        logger.addHandler(streamHandler);
                    } catch (SecurityException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return logger;
    }

    /**
     * Custom {@link Handler} for logging to a {@link PrintStream}.
     * 
     * @version 1.0
     * @since 1.0
     */
    private static class PrintStreamHandler extends Handler {

        private final PrintStream printStream;

        private Formatter formatter;

        public PrintStreamHandler(PrintStream printStream) {
            this.printStream = printStream;
        }

        @Override
        public void close() throws SecurityException {
            if (printStream != null) {
                printStream.close();
            }
        }

        @Override
        public void flush() {
            if (printStream != null) {
                printStream.flush();
            }
        }

        @Override
        public void publish(LogRecord record) {
            if (printStream != null && formatter != null) {
                printStream.print(formatter.format(record));
                printStream.flush();
            }
        }

        public void setFormatter(Formatter formatter) {
            this.formatter = formatter;
        }
        
    }

    /**
     * Custom {@link Handler} for logging to a {@link PrintWriter}.
     * 
     * @version 1.0
     * @since 1.0
     */
    private static class PrintWriterHandler extends Handler {
        
        private final PrintWriter writer;
        private Formatter formatter;

        public PrintWriterHandler(PrintWriter writer) {
            this.writer = writer;
        }

        @Override
        public void close() throws SecurityException {
            if (writer != null) {
                writer.close();
            }
            
        }

        @Override
        public void flush() {
            if (writer != null) {
                writer.flush();
            }
            
        }

        @Override
        public void publish(LogRecord record) {
            if (writer != null && formatter != null) {
                writer.println(formatter.format(record));
                writer.flush();
            }
        }

        public void setFormatter(Formatter formatter) {
            this.formatter = formatter;
        }

    }
}
