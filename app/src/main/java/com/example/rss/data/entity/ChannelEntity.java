package com.example.rss.data.entity;

import com.google.gson.annotations.SerializedName;

public class ChannelEntity {

	@SerializedName("id")
	private Long channelId;

	@SerializedName("title")
	private String title;

	@SerializedName("description")
	private String description;


	@SerializedName("description")
	private Long categoryId;

	@SerializedName("file")
	private Long fileId;

	@SerializedName("link")
	private String link;

	@SerializedName("last_build")
	private String lastBuild;

	@SerializedName("nextSyncDate")
	private String nextSyncDate;

	@SerializedName("cache_image")
	private Boolean cacheImage;

	@SerializedName("download_full_text")
	private Boolean downloadFullText;

	@SerializedName("onlyWifi")
	private Boolean onlyWifi;

	public Boolean getCacheImage() {
		return cacheImage;
	}

	public void setCacheImage(Boolean cacheImage) {
		this.cacheImage = cacheImage;
	}

	public String getNextSyncDate() {
		return nextSyncDate;
	}

	public void setNextSyncDate(String nextSyncDate) {
		this.nextSyncDate = nextSyncDate;
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

	public ChannelEntity() {
		//empty
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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
}
