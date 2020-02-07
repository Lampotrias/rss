package com.example.rss.data.entity;

public class ChannelEntity {

	//@SerializedName("id")
	private Long channelId;

	//@SerializedName("title")
	private String title;

	//@SerializedName("description")
	private String description;


	//@SerializedName("description")
	private Long categoryId;

	//@SerializedName("file")
	private Long fileId;

	//@SerializedName("link")
	private String link;


	private String sourceLink;

	//@SerializedName("last_build")
	private Long lastBuildDate;

	//@SerializedName("nextSyncDate")
	private Long nextSyncDate;

	//@SerializedName("cache_image")
	private Boolean cacheImage;

	//@SerializedName("download_full_text")
	private Boolean downloadFullText;

	//@SerializedName("onlyWifi")
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

	public Long getLastBuildDate() {
		return lastBuildDate;
	}

	public void setLastBuildDate(Long lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}
}
