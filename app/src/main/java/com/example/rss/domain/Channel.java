package com.example.rss.domain;


public class Channel {
	private Long channelId;
	private String title;
	private String description;
	private Long categoryId;
	private Long fileId;
	private String link;
	private String sourceLink;
	private Long lastBuild;
	private Boolean cacheImage;
	private Boolean downloadFullText;
	private Boolean onlyWifi;
	private Long nextSyncDate;

	public String getSourceLink() {
		return sourceLink;
	}

	public void setSourceLink(String sourceLink) {
		this.sourceLink = sourceLink;
	}
	public Long getChannelId() {
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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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

	public Long getLastBuild() {
		return lastBuild;
	}

	public void setLastBuild(Long lastBuild) {
		this.lastBuild = lastBuild;
	}

	public Boolean getCacheImage() {
		return cacheImage;
	}

	public void setCacheImage(Boolean cacheImage) {
		this.cacheImage = cacheImage;
	}

	public Boolean getDownloadFullText() {
		return downloadFullText;
	}

	public void setDownloadFullText(Boolean downloadFullText) {
		this.downloadFullText = downloadFullText;
	}

	public Boolean getOnlyWifi() {
		return onlyWifi;
	}

	public void setOnlyWifi(Boolean onlyWifi) {
		this.onlyWifi = onlyWifi;
	}

	public Long getNextSyncDate() {
		return nextSyncDate;
	}

	public void setNextSyncDate(Long nextSyncDate) {
		this.nextSyncDate = nextSyncDate;
	}
}