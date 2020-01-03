package com.example.rss.data.repository.datasource.impl;

import com.example.rss.data.database.AppDatabase;
import com.example.rss.data.database.dto.ChannelDTO;
import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.RowEntity;
import com.example.rss.data.repository.datasource.IDataStore;

import java.util.List;

import io.reactivex.Single;

public class DatabaseDataStore implements IDataStore {

	private AppDatabase appDatabase;
	public DatabaseDataStore(AppDatabase appDatabase) {
		this.appDatabase = appDatabase;
	}

	@Override
	public Single<Long> addChannel(ChannelEntity channel) {
		return appDatabase.channelDAO().insert(transform(channel));
	}

	@Override
	public Single<ChannelEntity> getChannelById(Integer id) {
		return null;
	}

	@Override
	public Single<List<RowEntity>> getRowsByChannelId(Integer id) {
		return null;
	}

	@Override
	public Single<String> getRssFeedContent(String path) {
		throw new UnsupportedOperationException("Operation is not available!!!");
	}

	ChannelDTO transform(ChannelEntity channelEntity){
		return new ChannelDTO();
	}
}
