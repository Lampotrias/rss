package com.example.rss.data.repository.datasource.impl;

import com.example.rss.data.database.AppDatabase;
import com.example.rss.data.entity.ChannelDTO;
import com.example.rss.data.repository.datasource.IDataStore;

import io.reactivex.Single;

public class DatabaseDataStore implements IDataStore {

	private AppDatabase appDatabase;
	public DatabaseDataStore(AppDatabase appDatabase) {
		this.appDatabase = appDatabase;
	}

	@Override
	public Single<Long> addChannel(ChannelDTO channelDTO) {
		return appDatabase.channelDAO().insert(channelDTO);
	}
}
