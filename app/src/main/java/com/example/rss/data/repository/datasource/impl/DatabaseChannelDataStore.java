package com.example.rss.data.repository.datasource.impl;

import com.example.rss.data.database.AppDatabase;
import com.example.rss.data.database.ChannelDatabaseMapper;
import com.example.rss.data.repository.datasource.IChannelDataStore;
import com.example.rss.domain.Channel;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public class DatabaseChannelDataStore implements IChannelDataStore {

	AppDatabase appDatabase;
	public DatabaseChannelDataStore(AppDatabase appDatabase) {
		this.appDatabase = appDatabase;
	}

	@Override
	public Single<String> getRssFeedContent(String path) {
		return null;
	}
}
