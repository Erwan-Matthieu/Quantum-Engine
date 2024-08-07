/**
 * Contains utility classes for configuration management and logging.
 * <p>
 * This package includes various utility classes designed to assist with application configuration and
 * logging. The utilities provided by this package facilitate easy management of application settings,
 * internationalization, and logging, ensuring robust and maintainable code.
 * </p>
 * <p>
 * The {@link com.quantum.utils.config} subpackage contains classes for handling application configuration
 * and internationalization. It provides facilities for loading and managing configuration properties and
 * localized messages, including: </p>
 * <ul>
 *     <li>{@link com.quantum.utils.config.ConfigurationProperties} - Manages application configuration properties,
 *         including loading, retrieving, setting, and saving properties.</li>
 *     <li>{@link com.quantum.utils.config.LanguageBundle} - Facilitates internationalization by providing
 *         access to localized messages based on the current language and locale settings.</li>
 * </ul>
 * 
 * <p>
 * The {@link com.quantum.utils.logging} subpackage provides utilities for logging, including custom log file
 * handling and cleanup. It includes: </p>
 * <ul>
 *     <li>{@link com.quantum.utils.logging.Logging} - Configures and manages logging with custom handlers
 *         and formatters. Supports logging to files, {@link java.io.PrintWriter}, and {@link java.io.PrintStream}.</li>
 *     <li>{@link com.quantum.utils.logging.Cleanup} - Manages log file retention by deleting old log files
 *         based on a specified retention period.</li>
 * </ul>
 * 
 */
package com.quantum.utils;
