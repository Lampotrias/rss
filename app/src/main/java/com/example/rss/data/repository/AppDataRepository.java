package com.example.rss.data.repository;


import com.example.rss.data.repository.datasource.ChannelDataStoreFactory;
import com.example.rss.data.repository.datasource.IDataStore;
import com.example.rss.domain.Channel;
import com.example.rss.domain.repositories.IRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class AppDataRepository implements IRepository {

	private final ChannelDataStoreFactory channelDataStoreFactory;

	@Inject
	public AppDataRepository(ChannelDataStoreFactory channelDataStoreFactory){
		this.channelDataStoreFactory = channelDataStoreFactory;

	}

	/***
	 * Return full rss page by url
	 *
	 * @param path
	 * @return
	 */
	@Override
	public Single<String> getRssFeedContent(String path) {
		final IDataStore dataStore = this.channelDataStoreFactory.create();
		return dataStore.getRssFeedContent(path);
	}

	@Override
	public Single<Long> addChannel(Channel channel) {
		return null;
	}
}
