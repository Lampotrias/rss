package com.example.rss.data.database.dto;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "file")
public class FileDTO {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Long fileId;

    @ColumnInfo(name = "type")
    private String type;

    @NonNull
    @ColumnInfo(name = "path")
    private String path;

    @NonNull
    @ColumnInfo(name = "description")
    private String description;

    @NonNull
    @ColumnInfo(name = "external")
    private Boolean external;

    @NonNull
    public Long getFileId() {
        return fileId;
    }

    public void setFileId(@NonNull Long fileId) {
        this.fileId = fileId;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    @NonNull
    public String getPath() {
        return path;
    }

    public void setPath(@NonNull String path) {
        this.path = path;
    }

    @NonNull
    public Boolean getExternal() {
        return external;
    }

    public void setExternal(@NonNull Boolean external) {
        this.external = external;
    }
}
