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

    @ColumnInfo(name = "channel_id")
    private Long channelId;

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
    private String pubDate;

    @NonNull
    @ColumnInfo(name = "enclosure")
    private Long enclosure;

    @NonNull
    @ColumnInfo(name = "is_read")
    private Boolean isRead;

    @NonNull
    @ColumnInfo(name = "is_favorite")
    private Boolean isFavorite;

    @NonNull
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(@NonNull Long itemId) {
        this.itemId = itemId;
    }

    @NonNull
    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(@NonNull Boolean favorite) {
        isFavorite = favorite;
    }

    @NonNull
    public Boolean getRead() {
        return isRead;
    }

    public void setRead(@NonNull Boolean read) {
        isRead = read;
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
    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(@NonNull String pubDate) {
        this.pubDate = pubDate;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }
    @NonNull
    public Long getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(@NonNull Long enclosure) {
        this.enclosure = enclosure;
    }
}
