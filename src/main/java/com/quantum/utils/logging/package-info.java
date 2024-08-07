/**
 * Provides utilities for logging and log file management.
 * <p>
 * This package includes classes for configuring and managing logging in an application. It offers
 * custom log file handlers, formatters, and utilities for log file cleanup. The logging utilities
 * support various output destinations such as files, {@link java.io.PrintWriter}, and {@link java.io.PrintStream}.
 * </p>
 * <p>
 * The {@link com.quantum.utils.logging.Logging} class provides a centralized logging system with custom
 * handlers for file-based logging and logging to standard output or error streams. It includes a
 * {@link java.util.logging.Formatter} to format log messages with timestamps, class names, method names,
 * log levels, and exception details. It also supports log file rolling based on the date.
 * </p>
 * <p>
 * The {@link com.quantum.utils.logging.Cleanup} class is responsible for managing log file retention
 * by periodically deleting old log files based on a specified retention period. It ensures that the
 * log directory does not accumulate unnecessary files, thus helping to manage disk space usage.
 * </p>
 */
package com.quantum.utils.logging;
