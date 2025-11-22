package com.shamseddin.dao;

import com.shamseddin.model.Document;

import java.util.List;
import java.util.Optional;

public interface DocumentDAO {

    Document addDocument(Document document);

    void updateDocument(Document document);

    void deleteDocument(int id);

    Optional<Document> getDocumentById(int id);

    List<Document> getDocumentsByVehicleId(int vehicleId);

    List<Document> getAllDocuments();
}
