package com.shamseddin.db;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the DatabaseConnection class.
 * - Verifies connection is established and valid.
 * - Ensures singleton behavior.
 */
public class DatabaseConnectionTest {

    private static Connection conn;

    @BeforeAll
    public static void setup() {
        try {
            conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            fail("Connection setup failed: " + e.getMessage());
        }
    }

    @Test
    public void testConnectionIsNotNull() {
        assertNotNull(conn, "Database connection should not be null");
    }

    @Test
    public void testConnectionIsValid() {
        try {
            assertTrue(conn.isValid(2), "Database connection should be valid");
        } catch (SQLException e) {
            fail("Failed to validate connection: " + e.getMessage());
        }
    }

    @Test
    public void testSingletonBehavior() {
        try {
            Connection secondConn = DatabaseConnection.getConnection();
            assertSame(conn, secondConn, "Should return the same connection instance");
        } catch (SQLException e) {
            fail("Second connection attempt failed: " + e.getMessage());
        }
    }

    @AfterAll
    public static void cleanup() {
        try {
            DatabaseConnection.shutdown();
        } catch (SQLException e) {
            fail("Shutdown failed: " + e.getMessage());
        }
    }
}

