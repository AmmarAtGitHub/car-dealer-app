package com.shamseddin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class TestDatabase {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "vehicle_dealer";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String SCHEMA_FILE = "src/main/resources/schema.sql";

    public static void main(String[] args) {
        // First, connect without specifying a database
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            // Check if database exists
            boolean dbExists = false;
            ResultSet resultSet = conn.getMetaData().getCatalogs();
            while (resultSet.next()) {
                if (resultSet.getString(1).equalsIgnoreCase(DB_NAME)) {
                    dbExists = true;
                    break;
                }
            }
            resultSet.close();

            if (!dbExists) {
                System.out.println("Database does not exist. Creating database: " + DB_NAME);
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
                System.out.println("Database created successfully!");
            }

            // Now connect to the specific database
            try (Connection dbConn = DriverManager.getConnection(DB_URL + DB_NAME, USERNAME, PASSWORD)) {
                System.out.println("Successfully connected to the database!");

                if (!dbExists) {
                    // Execute schema script
                    System.out.println("Executing schema script...");
                    String schema = new String(Files.readAllBytes(Paths.get(SCHEMA_FILE)));
                    
                    // Remove comments and split statements properly
                    String[] sqlStatements = schema.replaceAll("--.*", "")  // Remove single-line comments
                                                 .replaceAll("/\\*[\\s\\S]*?\\*/", "")  // Remove multi-line comments
                                                 .replaceAll("\\r\\n|\\r|\\n", " ")  // Normalize line endings
                                                 .split(";\\s*");  // Split on semicolons
                    
                    try (Statement schemaStmt = dbConn.createStatement()) {
                        for (String sql : sqlStatements) {
                            sql = sql.trim();
                            if (!sql.isEmpty() && !sql.matches("(?i)^(USE|SET|DELIMITER)\\b.*")) {
                                try {
                                    System.out.println("Executing: " + (sql.length() > 50 ? sql.substring(0, 50) + "..." : sql));
                                    schemaStmt.executeUpdate(sql);
                                } catch (SQLException e) {
                                    System.err.println("Error executing SQL: " + sql);
                                    throw e;
                                }
                            }
                        }
                        System.out.println("Schema created successfully!");
                    }
                }

                // List all tables
                DatabaseMetaData meta = dbConn.getMetaData();
                ResultSet tables = meta.getTables(null, null, "%", new String[]{"TABLE"});
                
                System.out.println("\nTables in the database:");
                while (tables.next()) {
                    System.out.println("- " + tables.getString("TABLE_NAME"));
                }
                
                // Test query (using a table that should exist)
                try (Statement queryStmt = dbConn.createStatement();
                     ResultSet rs = queryStmt.executeQuery("SHOW TABLES")) {
                    System.out.println("\nVerifying tables:");
                    while (rs.next()) {
                        System.out.println("Found table: " + rs.getString(1));
                    }
                }
                
            } catch (SQLException | IOException e) {
                System.err.println("Error working with database: " + e.getMessage());
                e.printStackTrace();
            }
            
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            System.err.println("Make sure MySQL server is running and the credentials are correct.");
            e.printStackTrace();
        }
    }
}
