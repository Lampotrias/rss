package com.example.rss.domain.xml;

public class XmlItemRawObject {
    private String title;
    private String link;
    private String description;
    private String pubDate;
    private String guid;
    private XmlFileRawObject enclosure;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public XmlFileRawObject getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(XmlFileRawObject enclosure) {
        this.enclosure = enclosure;
    }
}
