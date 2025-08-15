package com.shamseddin.dao;

import com.shamseddin.db.DatabaseConnection;
import com.shamseddin.model.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of VehicleDAO.
 * Handles CRUD operations using MySQL.
 */
public class  VehicleDAOImpl implements VehicleDAO {

    private static final Logger logger = LoggerFactory.getLogger(VehicleDAOImpl.class);
    private final Connection conn;

    public VehicleDAOImpl() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            logger.error("Failed to establish DB connection", e);
            throw new RuntimeException("Could not connect to database", e);
        }
    }


        @Override
        public void addVehicle(Vehicle vehicle) {
            String sql = "INSERT INTO vehicles (brand, model, year, vin, mileage, color ,asking_price) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, vehicle.getBrand());
                stmt.setString(2, vehicle.getModel());
                stmt.setInt(3, vehicle.getYear());
                stmt.setString(4, vehicle.getVin());
                stmt.setInt(5, vehicle.getMileage());
                stmt.setString(6, vehicle.getColor());
                stmt.setBigDecimal(7, vehicle.getAskingPrice());

                int rows = stmt.executeUpdate();
                if (rows == 0) {
                    throw new SQLException("Inserting vehicle failed, no rows affected.");
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        vehicle.setId(generatedId); // <- Set back into object
                        logger.info("Inserted vehicle with ID: {} and VIN: {}", generatedId, vehicle.getVin());
                    } else {
                        throw new SQLException("Inserting vehicle failed, no ID obtained.");
                    }
                }
            } catch (SQLException e) {
                logger.error("Error inserting vehicle", e);
                throw new RuntimeException("Database error while adding vehicle", e);
            }
        }


        @Override
    public void updateVehicle(Vehicle vehicle) {
        String sql = "UPDATE vehicles SET brand = ?, model = ?, year = ?, vin = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vehicle.getBrand());
            stmt.setString(2, vehicle.getModel());
            stmt.setInt(3, vehicle.getYear());
            stmt.setString(4, vehicle.getVin());
            //stmt.setDouble(5, vehicle.getPrice());
            stmt.setInt(6, vehicle.getId());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                logger.info("Updated vehicle with ID: {}", vehicle.getId());
            } else {
                logger.warn("No vehicle found with ID: {}", vehicle.getId());
            }
        } catch (SQLException e) {
            logger.error("Error updating vehicle", e);
            throw new RuntimeException("Database error while updating vehicle", e);
        }
    }

    @Override
    public void deleteVehicle(Vehicle vehicle) {
        String sql = "DELETE FROM vehicles WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, vehicle.getId());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                logger.info("Deleted vehicle with ID: {}", vehicle.getId());
            } else {
                logger.warn("No vehicle found with ID: {}", vehicle.getId());
            }
        } catch (SQLException e) {
            logger.error("Error deleting vehicle", e);
            throw new RuntimeException("Database error while deleting vehicle", e);
        }
    }

    @Override
    public Optional<Vehicle> getVehicle(int id) {
        String sql = "SELECT * FROM vehicles WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Vehicle v = mapRowToVehicle(rs);
                    logger.info("Retrieved vehicle with ID: {}", id);
                    return Optional.of(v);
                }
            }
        } catch (SQLException e) {
            logger.error("Error retrieving vehicle by ID", e);
            throw new RuntimeException("Database error while retrieving vehicle", e);
        }

        logger.warn("No vehicle found with ID: {}", id);
        return Optional.empty();
    }

    @Override
    public Optional<Vehicle> getVehicleByVin(String vin) {
        String sql = "SELECT * FROM vehicles WHERE vin = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vin);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Vehicle v = mapRowToVehicle(rs);
                    logger.info("Retrieved vehicle with VIN: {}", vin);
                    return Optional.of(v);
                }
            }
        } catch (SQLException e) {
            logger.error("Error retrieving vehicle by VIN", e);
            throw new RuntimeException("Database error while retrieving vehicle", e);
        }

        logger.warn("No vehicle found with VIN: {}", vin);
        return Optional.empty();
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                vehicles.add(mapRowToVehicle(rs));
            }

            logger.info("Retrieved all vehicles. Count: {}", vehicles.size());
        } catch (SQLException e) {
            logger.error("Error retrieving all vehicles", e);
            throw new RuntimeException("Database error while retrieving all vehicles", e);
        }

        return vehicles;
    }

    /**
     * Helper method to convert a ResultSet row into a Vehicle object.
     */
    private Vehicle mapRowToVehicle(ResultSet rs) throws SQLException {
        return new Vehicle(
                rs.getInt("id"),
                rs.getString("vin"),
                rs.getString("brand"),
                rs.getString("model"),
                rs.getInt("year"),
                rs.getInt("mileage"),
                rs.getString("color")
        );
    }
}

