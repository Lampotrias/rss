package com.example.rss.data.repository;


import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.mapper.ChannelEntityDataMapper;
import com.example.rss.data.repository.datasource.ChannelDataStoreFactory;
import com.example.rss.data.repository.datasource.IDataStore;
import com.example.rss.domain.Channel;
import com.example.rss.domain.Row;
import com.example.rss.domain.repositories.IRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class AppDataRepository implements IRepository {

	private final ChannelDataStoreFactory channelDataStoreFactory;
	private final ChannelEntityDataMapper channelEntityDataMapper;

	@Inject
	public AppDataRepository(ChannelDataStoreFactory channelDataStoreFactory, ChannelEntityDataMapper userEntityDataMapper){
		this.channelDataStoreFactory = channelDataStoreFactory;
		this.channelEntityDataMapper = userEntityDataMapper;

	}

	@Override
	public Single<String> getRssFeedContent(String path) {
		final IDataStore dataStore = channelDataStoreFactory.createNetwork();
		return dataStore.getRssFeedContent(path);
	}

	@Override
	public Single<Long> addChannel(Channel channel) {
		final IDataStore dataStore = channelDataStoreFactory.createPut();
		ChannelEntity channelEntity = channelEntityDataMapper.transform(channel);
		return dataStore.addChannel(channelEntity);
	}

	@Override
	public Single<Channel> getChannelById(Integer id) {
		final IDataStore dataStore = channelDataStoreFactory.createForChannel(id);
		return dataStore.getChannelById(id).map(channelEntityDataMapper::transform);
	}

	@Override
	public Single<List<Row>> getRowsByChannelId(Integer id) {
		return null;
	}
}
