package com.example.rss.data.repository;


import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.FileEntity;
import com.example.rss.data.entity.mapper.RepositoryEntityDataMapper;
import com.example.rss.data.repository.datasource.ChannelDataStoreFactory;
import com.example.rss.data.repository.datasource.IDataStore;
import com.example.rss.domain.Channel;
import com.example.rss.domain.File;
import com.example.rss.domain.Item;
import com.example.rss.domain.repositories.IRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class AppDataRepository implements IRepository {

	private final ChannelDataStoreFactory channelDataStoreFactory;
	private final RepositoryEntityDataMapper repositoryEntityDataMapper;

	@Inject
	public AppDataRepository(ChannelDataStoreFactory channelDataStoreFactory, RepositoryEntityDataMapper userEntityDataMapper){
		this.channelDataStoreFactory = channelDataStoreFactory;
		this.repositoryEntityDataMapper = userEntityDataMapper;

	}

	@Override
	public Single<String> getRssFeedContent(String path) {
		final IDataStore dataStore = channelDataStoreFactory.createNetwork();
		return dataStore.getRssFeedContent(path);
	}

	@Override
	public Single<Long> addChannel(Channel channel) {
		final IDataStore dataStore = channelDataStoreFactory.createPut();
		ChannelEntity channelEntity = repositoryEntityDataMapper.transform(channel);
		return dataStore.addChannel(channelEntity);
	}

	@Override
	public Single<Channel> getChannelById(Long id) {
		final IDataStore dataStore = channelDataStoreFactory.createForChannel(id);
		return dataStore.getChannelById(id).map(repositoryEntityDataMapper::transform);
	}

	@Override
	public Single<List<Item>> getRowsByChannelId(Long id) {
		return null;
	}

	@Override
	public Single<Long> addFile(File file) {
		final IDataStore dataStore = channelDataStoreFactory.createPut();
		//TODO addChannel cache
		FileEntity fileEntity = repositoryEntityDataMapper.transform(file);
		return dataStore.addFile(fileEntity);
	}
}
