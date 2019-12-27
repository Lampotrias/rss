package com.example.rss.domain;

public class Channel {

	private int channelId;

	public Channel() {}
	public Channel(int channelId) {
		this.channelId = channelId;
	}


	private String category;
	private String url;
	private Boolean cacheImage;
	private Boolean downloadFull;
	private Boolean onlyWifi;

	public int getChannelId() {
		return channelId;
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