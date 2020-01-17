package com.example.rss.data.database.dto;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

	//Custom category
	@NonNull
	@ColumnInfo(name = "category_id")
	private Long categoryId;

	@NonNull
	@ColumnInfo(name = "file_id")
	private Long fileId;

	@NonNull
	@ColumnInfo(name = "link")
	private String link;

	@NonNull
	@ColumnInfo(name = "last_build")
	private String lastBuild;

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
	private String nextSyncDate;

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
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(@NonNull Long categoryId) {
		this.categoryId = categoryId;
	}

	@NonNull
	public Long getFileId() {
		return fileId;
	}

	public void setFileId(@NonNull Long fileId) {
		this.fileId = fileId;
	}

	@NonNull
	public String getLink() {
		return link;
	}

	public void setLink(@NonNull String link) {
		this.link = link;
	}

	@NonNull
	public String getLastBuild() {
		return lastBuild;
	}

	public void setLastBuild(@NonNull String lastBuild) {
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
	public String getNextSyncDate() {
		return nextSyncDate;
	}

	public void setNextSyncDate(@NonNull String nextSyncDate) {
		this.nextSyncDate = nextSyncDate;
	}
}
