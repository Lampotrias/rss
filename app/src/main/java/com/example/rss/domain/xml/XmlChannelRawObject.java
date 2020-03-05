package com.example.rss.domain.xml;

public class XmlChannelRawObject implements XmlEntityHasFile{
	private String title;
	private String description;
	private String link;
	private String lastBuild;
	private XmlFileRawObject file;

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

	public XmlFileRawObject getEnclosure() {
		return file;
	}

	public void setEnclosure(XmlFileRawObject file) {
		this.file = file;
	}
}
