package com.example.rss.data.entity;

import com.google.gson.annotations.SerializedName;

public class FileEntity {
	@SerializedName("id")
	private int fileId;

	@SerializedName("title")
	private String title;

	@SerializedName("url")
	private String url;

	@SerializedName("description")
	private String description;

	/*@SerializedName("width")
	private Integer width;

	@SerializedName("height")
	private Integer height;*/

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/*public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	*/
}
