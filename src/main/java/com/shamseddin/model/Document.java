package com.shamseddin.model;

import java.time.LocalDateTime;

public class Document {
    private final int id;
    private final String title;
    private final String filePath;
    private final LocalDateTime uploadDate;
    private final DocumentType documentType;    
    private final long fileSize;

    public Document(int id, String title, String filePath,  
                   LocalDateTime uploadDate, DocumentType documentType, long fileSize) {
        this.id = id;
        this.title = title;
        this.filePath = filePath;
        this.uploadDate = uploadDate;
        this.documentType = documentType;
        this.fileSize = fileSize;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getFilePath() { return filePath; }
    public LocalDateTime getUploadDate() { return uploadDate; }
    public DocumentType getDocumentType() { return documentType; }
    public long getFileSize() { return fileSize; }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", filePath='" + filePath + '\'' +
                ", uploadDate=" + uploadDate +
                ", documentType='" + documentType + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}