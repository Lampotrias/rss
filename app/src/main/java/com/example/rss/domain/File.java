package com.example.rss.domain;


public class File {
	private Long fileId;

	private String path;

	private String description;

	private String type;

	private Boolean external;

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getExternal() {
		return external;
	}

	public void setExternal(Boolean external) {
		this.external = external;
	}
}
