package com.shamseddin.model;

import java.time.LocalDateTime;

public class Photo {
    private final int id;
    private final String filePath;
    private final String description;
    private final LocalDateTime uploadDate;
    private final String fileType;
    private final long fileSize;

    public Photo(int id, String filePath,  String description, LocalDateTime uploadDate, String fileType, long fileSize) {
        this.id = id;
        this.filePath = filePath;
        this.description = description;
        this.uploadDate = uploadDate;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public int getId() {
        return id;
    }
    

    public String getFilePath() {
        return filePath;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public String getFileType() {
        return fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", description='" + description + '\'' +
                ", uploadDate=" + uploadDate +
                ", fileType='" + fileType + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }

}
