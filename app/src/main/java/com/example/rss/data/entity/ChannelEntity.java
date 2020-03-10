package com.example.rss.data.entity;

public class ChannelEntity implements Entity {

    private Long channelId;
    private String title;
    private String description;
    private Long categoryId;
    private Long fileId;
    private String link;
    private String sourceLink;
    private Long lastBuildDate;
    private Long nextSyncDate;
    private Boolean cacheImage;
    private Boolean downloadFullText;
    private Boolean onlyWifi;

    public Boolean getCacheImage() {
        return cacheImage;
    }

    public void setCacheImage(Boolean cacheImage) {
        this.cacheImage = cacheImage;
    }

    public Long getNextSyncDate() {
        return nextSyncDate;
    }

    public void setNextSyncDate(Long nextSyncDate) {
        this.nextSyncDate = nextSyncDate;
    }

    public Boolean getDownloadFullText() {
        return downloadFullText;
    }

    public void setDownloadFullText(Boolean downloadFullText) {
        this.downloadFullText = downloadFullText;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public Boolean getOnlyWifi() {
        return onlyWifi;
    }

    public void setOnlyWifi(Boolean onlyWifi) {
        this.onlyWifi = onlyWifi;
    }

    public ChannelEntity() {
        //empty
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(Long lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }
}
