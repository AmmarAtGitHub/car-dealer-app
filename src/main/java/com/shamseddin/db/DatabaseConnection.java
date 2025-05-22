package com.shamseddin.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * A utility class responsible for managing the database connection.
 * - Implements lazy-loaded Singleton pattern
 * - Thread-safe connection handling
 * - External configuration from db.properties
 * - Includes graceful shutdown method
 */
public class DatabaseConnection {

    private static Connection connection;
    private static final Object LOCK = new Object(); // for thread-safety

    // Properties loaded from config file
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    // Static block to load configuration at class load time
    static {
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find db.properties. Ensure it is placed in src/main/resources.");
            }

            Properties props = new Properties();
            props.load(input);
            //make sure the properties file is loaded
            if (props.isEmpty()) {
                throw new RuntimeException("db.properties is empty.");
            }
            //make sure all the required settings are present in the properties file

            URL = requireProperty(props, "db.url");
            USER = requireProperty(props, "db.user");
            PASSWORD = requireProperty(props, "db.password");

        } catch (IOException e) {
            throw new RuntimeException("Failed to load database configuration", e);
        }
    }

    /**
     * Returns a thread-safe singleton database connection.
     * Reconnects if connection is invalid or closed.
     */
    public static Connection getConnection() throws SQLException {
        synchronized (LOCK) {
            //if the connection is not open, open it
            if (connection == null || connection.isClosed() || !connection.isValid(1)) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
            return connection;
        }
    }

    /**
     * Closes the database connection if it's open.
     * Useful to prevent resource leaks (e.g., on application shutdown).
     */
    public static void shutdown() throws SQLException {
        synchronized (LOCK) {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    /**
     * Utility method to validate required config properties.
     * @param props the loaded properties
     * @param key the key to check
     * @return the value if present and non-empty
     */
    private static String requireProperty(Properties props, String key) {
        String value = props.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new RuntimeException("Missing required property: " + key);
        }
        return value;
    }
}