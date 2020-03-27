package com.example.rss.data.repository;


import com.example.rss.data.entity.mapper.RepositoryEntityDataMapper;
import com.example.rss.data.repository.datasource.ChannelDataStoreFactory;
import com.example.rss.data.repository.datasource.IDataStore;
import com.example.rss.domain.Category;
import com.example.rss.domain.Channel;
import com.example.rss.domain.Favorite;
import com.example.rss.domain.File;
import com.example.rss.domain.Item;
import com.example.rss.domain.repositories.IRepository;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Singleton
public class AppDataRepository implements IRepository {

    private final ChannelDataStoreFactory channelDataStoreFactory;
    private final RepositoryEntityDataMapper repositoryEntityDataMapper;

    @Inject
    public AppDataRepository(ChannelDataStoreFactory channelDataStoreFactory, RepositoryEntityDataMapper userEntityDataMapper) {
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
        final IDataStore dataStore = channelDataStoreFactory.createForChannel(null);
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
    public Maybe<List<Long>> insertManyItems(List<Item> items) {
        final IDataStore dataStore = channelDataStoreFactory.createForItems(null);
        return dataStore.InsertManyItems(repositoryEntityDataMapper.transformItemsToEntity(items));
    }

    @Override
    public Maybe<Item> getItemByUniqueId(String hash) {
        final IDataStore dataStore = channelDataStoreFactory.createForItems(null);
        return dataStore.getItemByUniqueId(hash).map(repositoryEntityDataMapper::transform);
    }

    @Override
    public Maybe<Integer> deleteAllItems() {
        final IDataStore dataStore = channelDataStoreFactory.createForItems(null);
        return dataStore.deleteAllItems();
    }

    @Override
    public Maybe<Integer> deleteItemById(Long id) {
        final IDataStore dataStore = channelDataStoreFactory.createForItems(null);
        return dataStore.deleteItemById(id);
    }

    @Override
    public Maybe<Integer> deleteItemsByChannelId(Long id) {
        final IDataStore dataStore = channelDataStoreFactory.createForItems(null);
        return dataStore.deleteItemsByChannelId(id);
    }

    @Override
    public Maybe<Integer> updateReadById(Long id, Boolean isRead) {
        final IDataStore dataStore = channelDataStoreFactory.createForItems(null);
        return dataStore.updateReadById(id, isRead);
    }

    @Override
    public Maybe<Integer> getCountItemsByChannel(Long id) {
        final IDataStore dataStore = channelDataStoreFactory.createForItems(null);
        return dataStore.getCountItemsByChannel(id);
    }

    @Override
    public Maybe<Integer> getPosItemInChannelQueue(Long channelId, Long itemId) {
        final IDataStore dataStore = channelDataStoreFactory.createForItems(null);
        return dataStore.getPosItemInChannelQueue(channelId, itemId);
    }

    @Override
    public Maybe<Integer> getCountItemsForChannel(Long channelId) {
        final IDataStore dataStore = channelDataStoreFactory.createForItems(null);
        return dataStore.getCountItemsForChannel(channelId);
    }

    @Override
    public Maybe<List<Item>> getItemsWithOffsetByChannel(Long channelId, Integer offset, Integer limit) {
        final IDataStore dataStore = channelDataStoreFactory.createForItems(null);
        return dataStore.getItemsWithOffsetByChannel(channelId, offset, limit).map(repositoryEntityDataMapper::transformEntityToItems);
    }

    @Override
    public Single<Channel> getChannelByUrl(String url) {
        final IDataStore dataStore = channelDataStoreFactory.createForChannel(null);
        return dataStore.getChannelByUrl(url).map(repositoryEntityDataMapper::transform);
    }

    @Override
    public Maybe<Integer> updateChannel(Channel channel) {
        final IDataStore dataStore = channelDataStoreFactory.createForChannel(null);
        return dataStore.updateChannel(repositoryEntityDataMapper.transform(channel));
    }

    @Override
    public Maybe<Integer> deleteAllChannels() {
        final IDataStore dataStore = channelDataStoreFactory.createForChannel(null);
        return dataStore.deleteAllChannels();
    }

    @Override
    public Maybe<Integer> deleteChannelById(Long id) {
        final IDataStore dataStore = channelDataStoreFactory.createForChannel(null);
        return dataStore.deleteChannelById(id);
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
    public Maybe<Long> addFile(File file) {
        final IDataStore dataStore = channelDataStoreFactory.createForFile(null);
        return dataStore.addFile(repositoryEntityDataMapper.transform(file));
    }

    @Override
    public Maybe<File> getFileById(Long id) {
        final IDataStore dataStore = channelDataStoreFactory.createForFile(id);
        return dataStore.getFileById(id).map(repositoryEntityDataMapper::transform);

    }

    @Override
    public Maybe<Integer> deleteFileById(Long id) {
        final IDataStore dataStore = channelDataStoreFactory.createForFile(id);
        return dataStore.deleteFileById(id);
    }

    @Override
    public Maybe<Integer> updateNextExec(Long channelId, Long nextTimestamp) {
        final IDataStore dataStore = channelDataStoreFactory.createForChannel(null);
        return dataStore.updateNextExec(channelId, nextTimestamp);
    }

    @Override
    public Maybe<Integer> deleteFavByItemBy(Long id) {
        final IDataStore dataStore = channelDataStoreFactory.createForFavorite(null);
        return dataStore.deleteFavByItemBy(id);
    }

    @Override
    public Maybe<Long> insertFavorite(Favorite favorite) {
        final IDataStore dataStore = channelDataStoreFactory.createForFavorite(null);
        return dataStore.insertFavorite(RepositoryEntityDataMapper.transform(favorite));
    }

    @Override
    public Maybe<Integer> deleteAllFavorites() {
        final IDataStore dataStore = channelDataStoreFactory.createForFavorite(null);
        return dataStore.deleteAllFavorites();
    }
}
