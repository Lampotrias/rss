package com.example.rss.domain;


public class Channel {
	private Long channelId;
	private String title;
	private String description;
	private Long categoryId;
	private Long fileId;
	private String link;
	private String lastBuild;
	private Boolean cacheImage;
	private Boolean downloadFullText;
	private Boolean onlyWifi;
	private String nextSyncDate;

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

	public String getLastBuild() {
		return lastBuild;
	}

	public void setLastBuild(String lastBuild) {
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

	public String getNextSyncDate() {
		return nextSyncDate;
	}

	public void setNextSyncDate(String nextSyncDate) {
		this.nextSyncDate = nextSyncDate;
	}
}