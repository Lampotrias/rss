package com.example.rss.data.repository;


import android.util.Log;

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
	public Maybe<Channel> getChannelById(Long id) {
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
	public Maybe<List<Item>> getAllItems() {
		final IDataStore dataStore = channelDataStoreFactory.createForItems(null);
		return dataStore.getAllItems().map(repositoryEntityDataMapper::transformEntityToItems);
	}

	@Override
	public Maybe<List<Long>> InsertManyItems(List<Item> items) {
		final IDataStore dataStore = channelDataStoreFactory.createForItems(null);
		return dataStore.InsertManyItems(repositoryEntityDataMapper.transformItemsToEntity(items));
	}

	@Override
	public Single<Item> getItemByUniqueId(String hash) {
		final IDataStore dataStore = channelDataStoreFactory.createForItems(null);
		return dataStore.getItemByUniqueId(hash).map(repositoryEntityDataMapper::transform);
	}

	@Override
	public Completable deleteAllItems() {
		final IDataStore dataStore = channelDataStoreFactory.createForItems(null);
		return dataStore.deleteAllItems();
	}

	@Override
	public Single<Channel> getChannelByUrl(String url) {
		final IDataStore dataStore = channelDataStoreFactory.createForChannel(null);
		return dataStore.getChannelByUrl(url).map(repositoryEntityDataMapper::transform);
	}

	@Override
	public Single<Integer> updateChannel(Channel channel) {
		final IDataStore dataStore = channelDataStoreFactory.createForChannel(null);
		return dataStore.updateChannel(repositoryEntityDataMapper.transform(channel));
	}

	@Override
	public Completable deleteAllChannels() {
		final IDataStore dataStore = channelDataStoreFactory.createForChannel(null);
		return dataStore.deleteAllChannels();
	}

	@Override
	public Maybe<List<Category>> getCategoriesByType(String mType) {
		final IDataStore dataStore = channelDataStoreFactory.createForCategory(null);
		return dataStore.getCategoriesByType(mType).map(repositoryEntityDataMapper::transformCategories);
	}

	@Override
	public Maybe<Category> getCategoryById(Long id) {
		final IDataStore dataStore = channelDataStoreFactory.createForCategory(null);
		return dataStore.getCategoryById(id).map(repositoryEntityDataMapper::transform);
	}

	@Override
	public Maybe<Long> addCategory(Category category) {
		final IDataStore dataStore = channelDataStoreFactory.createForCategory(null);
		return dataStore.addCategory(repositoryEntityDataMapper.transform(category));
	}

	@Override
	public Single<Long> addFile(File file) {
		final IDataStore dataStore = channelDataStoreFactory.createPut();
		return dataStore.addFile(repositoryEntityDataMapper.transform(file));
	}

	@Override
	public Maybe<File> getFileById(Long id) {
		final IDataStore dataStore = channelDataStoreFactory.createForFile(id);
		return dataStore.getFileById(id).map(repositoryEntityDataMapper::transform);

	}

	@Override
	public Maybe<Integer> updateNextExec(Long channelId, Long nextTimestamp) {
		final IDataStore dataStore = channelDataStoreFactory.createForChannel(null);
		return dataStore.updateNextExec(channelId, nextTimestamp);
	}
}
