package com.example.rss.data.database.dto;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "item")
public class ItemDTO {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Long itemId;

    @NonNull
    @ColumnInfo(name = "guid")
    private Long guid;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "description")
    private String description;

    @NonNull
    @ColumnInfo(name = "link")
    private String link;

    @NonNull
    @ColumnInfo(name = "pub_date")
    private Date pubDate;

    @NonNull
    @ColumnInfo(name = "encosure")
    private Long encosure;

    @NonNull
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(@NonNull Long itemId) {
        this.itemId = itemId;
    }

    @NonNull
    public Long getGuid() {
        return guid;
    }

    public void setGuid(@NonNull Long guid) {
        this.guid = guid;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getLink() {
        return link;
    }

    public void setLink(@NonNull String link) {
        this.link = link;
    }

    @NonNull
    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(@NonNull Date pubDate) {
        this.pubDate = pubDate;
    }

    @NonNull
    public Long getEncosure() {
        return encosure;
    }

    public void setEncosure(@NonNull Long encosure) {
        this.encosure = encosure;
    }
}
