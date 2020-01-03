package com.example.rss.data.database.dto;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "channel")
public class ChannelDTO {

	@PrimaryKey(autoGenerate = true)
	@NonNull
	@ColumnInfo(name = "id")
	private Long channelId;

	@NonNull
	@ColumnInfo(name = "title")
	private String title;

	@NonNull
	@ColumnInfo(name = "description")
	private String description;

	@NonNull
	@ColumnInfo(name = "category_id")
	private String categoryId;

	@NonNull
	@ColumnInfo(name = "image_id")
	private Integer imageId;

	@NonNull
	@ColumnInfo(name = "link")
	private String link;

	@NonNull
	@ColumnInfo(name = "last_build")
	private Date lastBuild;

	//*****************
	//System fields ***
	//*****************
	@NonNull
	@ColumnInfo(name = "cache_image")
	private Boolean cacheImage;

	@NonNull
	@ColumnInfo(name = "download_full_text")
	private Boolean downloadFullText;

	@NonNull
	@ColumnInfo(name = "only_wifi")
	private Boolean onlyWifi;

	@NonNull
	@ColumnInfo(name = "next_sync_date")
	private Boolean nextSyncDate;

	@NonNull
	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(@NonNull Long channelId) {
		this.channelId = channelId;
	}

	@NonNull
	public String getTitle() {
		return title;
	}

	public void setTitle(@NonNull String title) {
		this.title = title;
	}

	@NonNull
	public String getDescription() {
		return description;
	}

	public void setDescription(@NonNull String description) {
		this.description = description;
	}

	@NonNull
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(@NonNull String categoryId) {
		this.categoryId = categoryId;
	}

	@NonNull
	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(@NonNull Integer imageId) {
		this.imageId = imageId;
	}

	@NonNull
	public String getLink() {
		return link;
	}

	public void setLink(@NonNull String link) {
		this.link = link;
	}

	@NonNull
	public Date getLastBuild() {
		return lastBuild;
	}

	public void setLastBuild(@NonNull Date lastBuild) {
		this.lastBuild = lastBuild;
	}

	@NonNull
	public Boolean getCacheImage() {
		return cacheImage;
	}

	public void setCacheImage(@NonNull Boolean cacheImage) {
		this.cacheImage = cacheImage;
	}

	@NonNull
	public Boolean getDownloadFullText() {
		return downloadFullText;
	}

	public void setDownloadFullText(@NonNull Boolean downloadFullText) {
		this.downloadFullText = downloadFullText;
	}

	@NonNull
	public Boolean getOnlyWifi() {
		return onlyWifi;
	}

	public void setOnlyWifi(@NonNull Boolean onlyWifi) {
		this.onlyWifi = onlyWifi;
	}

	@NonNull
	public Boolean getNextSyncDate() {
		return nextSyncDate;
	}

	public void setNextSyncDate(@NonNull Boolean nextSyncDate) {
		this.nextSyncDate = nextSyncDate;
	}
}
