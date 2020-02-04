package com.example.rss.data.repository.datasource.impl;

import com.example.rss.data.database.AppDatabase;
import com.example.rss.data.database.mapper.ChannelDatabaseMapper;
import com.example.rss.data.entity.CategoryEntity;
import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.FileEntity;
import com.example.rss.data.entity.ItemEntity;
import com.example.rss.data.repository.datasource.IDataStore;

import java.io.InputStream;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class DatabaseDataStore implements IDataStore {

	private final AppDatabase appDatabase;

	public DatabaseDataStore(AppDatabase appDatabase) {
		this.appDatabase = appDatabase;
	}

	@Override
	public Single<Long> addChannel(ChannelEntity channel) {
		return appDatabase.channelDAO().insert(ChannelDatabaseMapper.transform(channel));
	}

	@Override
	public Single<ChannelEntity> getChannelById(Long id) {
		return appDatabase.channelDAO().getChannelById(id).map(ChannelDatabaseMapper::transform);
	}

	@Override
	public Single<List<ItemEntity>> getItemsByChannelId(Long id) {
		return null;
	}

	@Override
	public Single<ChannelEntity> getChannelByUrl(String url) {
		return appDatabase.channelDAO().getChannelByUrl(url).map(ChannelDatabaseMapper::transform);
	}

	@Override
	public Flowable<List<ChannelEntity>> getAllChannels() {
		return appDatabase.channelDAO().getAllChannels().map(ChannelDatabaseMapper::transformChannels);
	}

	@Override
	public Single<Long> addFile(FileEntity fileEntity) {
		return appDatabase.fileDAO().insert(ChannelDatabaseMapper.transform(fileEntity));
	}

	@Override
	public Flowable<List<CategoryEntity>> getCategoriesByType(String mType) {
		return appDatabase.categoryDAO().getCategoriesByType(mType).map(ChannelDatabaseMapper::transformCategories);
	}

	@Override
	public Single<InputStream> getRssFeedContent(String path) {
		throw new UnsupportedOperationException("Operation is not available!!!");
	}
}
