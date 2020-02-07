package com.example.rss.data.repository;


import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.FileEntity;
import com.example.rss.data.entity.mapper.RepositoryEntityDataMapper;
import com.example.rss.data.repository.datasource.ChannelDataStoreFactory;
import com.example.rss.data.repository.datasource.IDataStore;
import com.example.rss.domain.Category;
import com.example.rss.domain.Channel;
import com.example.rss.domain.File;
import com.example.rss.domain.Item;
import com.example.rss.domain.repositories.IRepository;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
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
	public Maybe<InputStream> getRssFeedContent(String path) {
		final IDataStore dataStore = channelDataStoreFactory.createNetwork();
		return dataStore.getRssFeedContent(path);
	}

	@Override
	public Maybe<Long> addChannel(Channel channel) {
		final IDataStore dataStore = channelDataStoreFactory.createPut();
		return dataStore.addChannel(repositoryEntityDataMapper.transform(channel));
	}

	@Override
	public Single<Channel> getChannelById(Long id) {
		final IDataStore dataStore = channelDataStoreFactory.createForChannel(id);
		return dataStore.getChannelById(id).map(repositoryEntityDataMapper::transform);
	}

	@Override
	public Maybe<List<Channel>> getAllChannels() {
		final IDataStore dataStore = channelDataStoreFactory.createForChannel(null);
		return dataStore.getAllChannels().map(repositoryEntityDataMapper::transformChannels);
	}

	@Override
	public Maybe<List<Item>> getItemsByChannelId(Long id) {
		final IDataStore dataStore = channelDataStoreFactory.createForItems(null);
		return dataStore.getItemsByChannelId(id).map(repositoryEntityDataMapper::transformEntityToItems);
	}

	@Override
	public Maybe<List<Long>> InsertManyItems(List<Item> items) {
		final IDataStore dataStore = channelDataStoreFactory.createForItems(null);
		return dataStore.InsertManyItems(repositoryEntityDataMapper.transformItemsToEntity(items));
	}

	@Override
	public Observable<Item> getItemByUniqueId(String hash) {
		final IDataStore dataStore = channelDataStoreFactory.createForItems(null);
		return dataStore.getItemByUniqueId(hash).map(repositoryEntityDataMapper::transform);
	}

	@Override
	public Single<Channel> getChannelByUrl(String url) {
		final IDataStore dataStore = channelDataStoreFactory.createForChannel(null);
		return dataStore.getChannelByUrl(url).map(repositoryEntityDataMapper::transform);
	}

	@Override
	public Flowable<List<Category>> getCategoriesByType(String mType) {
		final IDataStore dataStore = channelDataStoreFactory.createForCategory(null);
		return dataStore.getCategoriesByType(mType).map(repositoryEntityDataMapper::transformCategories);
	}

	@Override
	public Single<Long> addFile(File file) {
		final IDataStore dataStore = channelDataStoreFactory.createPut();
		return dataStore.addFile(repositoryEntityDataMapper.transform(file));
	}

	@Override
	public Flowable<File> getFileById(Long id) {
		final IDataStore dataStore = channelDataStoreFactory.createForFile(id);
		return dataStore.getFileById(id).map(repositoryEntityDataMapper::transform);

	}
}
