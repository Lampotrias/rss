package com.example.rss.data.database.dto;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "files")
public class FileDTO {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Long fileId;

    @NonNull
    @ColumnInfo(name = "type")
    private String type;

    @NonNull
    @ColumnInfo(name = "path")
    private String path;

    @NonNull
    @ColumnInfo(name = "size")
    private Long size;

    @NonNull
    @ColumnInfo(name = "external")
    private Long external;

    @NonNull
    public Long getFileId() {
        return fileId;
    }

    public void setFileId(@NonNull Long fileId) {
        this.fileId = fileId;
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
    public Long getSize() {
        return size;
    }

    public void setSize(@NonNull Long size) {
        this.size = size;
    }

    @NonNull
    public Long getExternal() {
        return external;
    }

    public void setExternal(@NonNull Long external) {
        this.external = external;
    }
}
