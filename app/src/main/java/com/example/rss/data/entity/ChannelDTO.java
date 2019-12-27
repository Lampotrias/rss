package com.example.rss.data.entity;


import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "channel")
public class ChannelDTO {

	@PrimaryKey(autoGenerate = true)
	@NonNull
	private Long id;

	@NonNull
	@ColumnInfo(name = "category")
	private String category;

	@NonNull
	@ColumnInfo(name = "url")
	private String url;

	@NonNull
	@ColumnInfo(name = "cacheImage")
	private Boolean cacheImage;

	@NonNull
	@ColumnInfo(name = "downloadFull")
	private Boolean downloadFull;

	@NonNull
	@ColumnInfo(name = "onlyWifi")
	private Boolean onlyWifi;

	@NonNull
	public Long getId() {
		return id;
	}

	public void setId(@NonNull Long id) {
		this.id = id;
	}

	@NonNull
	public String getCategory() {
		return category;
	}

	public void setCategory(@NonNull String category) {
		this.category = category;
	}

	@NonNull
	public String getUrl() {
		return url;
	}

	public void setUrl(@NonNull String url) {
		this.url = url;
	}

	@NonNull
	public Boolean getCacheImage() {
		return cacheImage;
	}

	public void setCacheImage(@NonNull Boolean cacheImage) {
		this.cacheImage = cacheImage;
	}

	@NonNull
	public Boolean getDownloadFull() {
		return downloadFull;
	}

	public void setDownloadFull(@NonNull Boolean downloadFull) {
		this.downloadFull = downloadFull;
	}

	@NonNull
	public Boolean getOnlyWifi() {
		return onlyWifi;
	}

	public void setOnlyWifi(@NonNull Boolean onlyWifi) {
		this.onlyWifi = onlyWifi;
	}
}
