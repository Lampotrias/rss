package com.example.rss.data.repository.datasource.impl;

import com.example.rss.data.database.AppDatabase;
import com.example.rss.data.database.mapper.ChannelDatabaseMapper;
import com.example.rss.data.entity.CategoryEntity;
import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.FileEntity;
import com.example.rss.data.entity.ItemEntity;
import com.example.rss.data.repository.datasource.IDataStore;
import com.example.rss.domain.File;
import com.example.rss.domain.Item;

import java.io.InputStream;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public class DatabaseDataStore implements IDataStore {

	private final AppDatabase appDatabase;

	public DatabaseDataStore(AppDatabase appDatabase) {
		this.appDatabase = appDatabase;
	}

	@Override
	public Maybe<Long> addChannel(ChannelEntity channel) {
		return appDatabase.channelDAO().insert(ChannelDatabaseMapper.transform(channel));
	}

	@Override
	public Single<ChannelEntity> getChannelById(Long id) {
		return appDatabase.channelDAO().getChannelById(id).map(ChannelDatabaseMapper::transform);
	}

	@Override
	public Maybe<List<ItemEntity>> getItemsByChannelId(Long id) {
		return appDatabase.itemDAO().getItemsByChannelId(id).map(ChannelDatabaseMapper::transformItemsDtoToEntity);
	}

	@Override
	public Maybe<List<Long>> InsertManyItems(List<ItemEntity> itemEntities) {
		return appDatabase.itemDAO().insertAll(ChannelDatabaseMapper.transformItems(itemEntities));
	}

	@Override
	public Observable<ItemEntity> getItemByUniqueId(String hash) {
		return appDatabase.itemDAO().getItemByUniqueId(hash).map(ChannelDatabaseMapper::transform);
	}

	@Override
	public Single<ChannelEntity> getChannelByUrl(String url) {
		return appDatabase.channelDAO().getChannelByUrl(url).map(ChannelDatabaseMapper::transform);
	}

	@Override
	public Maybe<List<ChannelEntity>> getAllChannels() {
		return appDatabase.channelDAO().getAllChannels().map(ChannelDatabaseMapper::transformChannels);
	}

	@Override
	public Single<Long> addFile(FileEntity fileEntity) {
		return appDatabase.fileDAO().insert(ChannelDatabaseMapper.transform(fileEntity));
	}

	@Override
	public Flowable<FileEntity> getFileById(Long id) {
		return appDatabase.fileDAO().getFileById(id).map(ChannelDatabaseMapper::transform);
	}

	@Override
	public Flowable<List<CategoryEntity>> getCategoriesByType(String mType) {
		return appDatabase.categoryDAO().getCategoriesByType(mType).map(ChannelDatabaseMapper::transformCategories);
	}

	@Override
	public Maybe<InputStream> getRssFeedContent(String path) {
		throw new UnsupportedOperationException("Operation is not available!!!");
	}
}
