package com.example.rss.domain;


import java.util.Date;

public class Channel {
	private Long channelId;
	private String title;
	private String description;
	private String categoryId;
	private Integer imageId;
	private String link;
	private Date lastBuild;
	private Boolean cacheImage;
	private Boolean downloadFullText;
	private Boolean onlyWifi;
	private Boolean nextSyncDate;

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

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getLastBuild() {
		return lastBuild;
	}

	public void setLastBuild(Date lastBuild) {
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

	public Boolean getNextSyncDate() {
		return nextSyncDate;
	}

	public void setNextSyncDate(Boolean nextSyncDate) {
		this.nextSyncDate = nextSyncDate;
	}
}