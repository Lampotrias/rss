package com.example.rss.data.entity;

import com.google.gson.annotations.SerializedName;

public class FileEntity {
	@SerializedName("id")
	private Long fileId;

	@SerializedName("title")
	private String title;

	@SerializedName("path")
	private String path;

	@SerializedName("description")
	private String description;

	@SerializedName("type")
	private String type;

	@SerializedName("external")
	private Boolean external;

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getExternal() {
		return external;
	}

	public void setExternal(Boolean external) {
		this.external = external;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}
