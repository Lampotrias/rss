package com.example.rss.data.entity;

import com.google.gson.annotations.SerializedName;

public class ChannelEntity {

	@SerializedName("id")
	private Long channelId;

	@SerializedName("title")
	private String title;

	@SerializedName("description")
	private String description;

	@SerializedName("image")
	private FileEntity image;

	@SerializedName("link")
	private String link;

	@SerializedName("last_build")
	private String lastBuild;


	public ChannelEntity() {
		//empty
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

	public FileEntity getImage() {
		return image;
	}

	public void setImage(FileEntity image) {
		this.image = image;
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
