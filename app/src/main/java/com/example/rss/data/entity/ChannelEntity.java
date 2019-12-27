package com.example.rss.data.entity;

import com.google.gson.annotations.SerializedName;

public class ChannelEntity {
	@SerializedName("id")
	private int channelId;

	@SerializedName("category")
	private String category;

	@SerializedName("url")
	private String url;

	@SerializedName("cacheImage")
	private Boolean cacheImage;

	@SerializedName("downloadFull")
	private Boolean downloadFull;

	@SerializedName("onlyWifi")
	private Boolean onlyWifi;

	public ChannelEntity() {
		//empty
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getCacheImage() {
		return cacheImage;
	}

	public void setCacheImage(Boolean cacheImage) {
		this.cacheImage = cacheImage;
	}

	public Boolean getDownloadFull() {
		return downloadFull;
	}

	public void setDownloadFull(Boolean downloadFull) {
		this.downloadFull = downloadFull;
	}

	public Boolean getOnlyWifi() {
		return onlyWifi;
	}

	public void setOnlyWifi(Boolean onlyWifi) {
		this.onlyWifi = onlyWifi;
	}
}
