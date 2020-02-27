package com.example.rss.presentation.itemList.adapter;

import com.example.rss.domain.Item;

import java.util.Objects;

public class ItemModel {
    private Long itemId;
    private String guid;
    private String title;
    private String description;
    private String link;
    private String pubDate;
    private String enclosure;
    private Boolean isRead = false;
    private Boolean isStar = false;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public Boolean getStar() {
        return isStar;
    }

    public void setStar(Boolean star) {
        isStar = star;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemModel itemModel = (ItemModel) o;
        return itemId.equals(itemModel.itemId) &&
                title.equals(itemModel.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, title);
    }
}
