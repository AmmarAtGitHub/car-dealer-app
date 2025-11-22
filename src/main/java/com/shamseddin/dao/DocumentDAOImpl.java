package com.shamseddin.dao;

import com.shamseddin.db.DatabaseConnection;
import com.shamseddin.model.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DocumentDAOImpl implements DocumentDAO {

    private static final Logger logger = LoggerFactory.getLogger(DocumentDAOImpl.class);
    private final Connection conn;

    public DocumentDAOImpl() {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            logger.error("Failed to establish DB connection", e);
            throw new RuntimeException("Could not connect to database", e);
        }
    }

    @Override
    public Document addDocument(Document document) {
        String sql = "INSERT INTO documents (vehicle_id, file_path, file_type, uploaded_at) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, document.getVehicleId());
            stmt.setString(2, document.getFilePath());
            stmt.setString(3, document.getFileType());
            stmt.setTimestamp(4, Timestamp.valueOf(document.getUploadedAt()));

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Creating document failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    Document newDoc = new Document(
                            generatedId,
                            document.getVehicleId(),
                            document.getFilePath(),
                            document.getFileType(),
                            document.getUploadedAt()
                    );
                    return newDoc;
                }
            }

            throw new SQLException("Creating document failed, no ID obtained.");

        } catch (SQLException e) {
            logger.error("Error adding document", e);
            throw new RuntimeException("Failed to add document", e);
        }
    }

    @Override
    public void updateDocument(Document document) {
        String sql = "UPDATE documents SET file_path = ?, file_type = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, document.getFilePath());
            stmt.setString(2, document.getFileType());
            stmt.setInt(3, document.getId());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                logger.info("Document updated: ID {}", document.getId());
            } else {
                logger.warn("Document not found: ID {}", document.getId());
            }

        } catch (SQLException e) {
            logger.error("Error updating document", e);
            throw new RuntimeException("Failed to update document", e);
        }
    }

    @Override
    public void deleteDocument(int id) {
        String sql = "DELETE FROM documents WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                logger.info("Document deleted: ID {}", id);
            } else {
                logger.warn("Document not found: ID {}", id);
            }

        } catch (SQLException e) {
            logger.error("Error deleting document", e);
            throw new RuntimeException("Failed to delete document", e);
        }
    }

    @Override
    public Optional<Document> getDocumentById(int id) {
        String sql = "SELECT * FROM documents WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToDocument(rs));
                }
            }

        } catch (SQLException e) {
            logger.error("Error retrieving document by ID", e);
            throw new RuntimeException("Failed to retrieve document", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Document> getDocumentsByVehicleId(int vehicleId) {
        String sql = "SELECT * FROM documents WHERE vehicle_id = ?";
        List<Document> documents = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, vehicleId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    documents.add(mapRowToDocument(rs));
                }
            }

        } catch (SQLException e) {
            logger.error("Error retrieving documents by vehicle ID", e);
            throw new RuntimeException("Failed to retrieve documents", e);
        }

        return documents;
    }

    @Override
    public List<Document> getAllDocuments() {
        String sql = "SELECT * FROM documents";
        List<Document> documents = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                documents.add(mapRowToDocument(rs));
            }

        } catch (SQLException e) {
            logger.error("Error retrieving all documents", e);
            throw new RuntimeException("Failed to retrieve all documents", e);
        }

        return documents;
    }

    private Document mapRowToDocument(ResultSet rs) throws SQLException {
        return new Document(
                rs.getInt("id"),
                rs.getInt("vehicle_id"),
                rs.getString("file_path"),
                rs.getString("file_type"),
                rs.getTimestamp("uploaded_at").toLocalDateTime()
        );
    }
}
