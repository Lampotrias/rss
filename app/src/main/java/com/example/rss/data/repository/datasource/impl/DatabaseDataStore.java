package com.example.rss.data.repository.datasource.impl;


import com.example.rss.data.cache.Cache;
import com.example.rss.data.database.AppDatabase;
import com.example.rss.data.database.mapper.ChannelDatabaseMapper;
import com.example.rss.data.entity.CategoryEntity;
import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.FavoriteEntity;
import com.example.rss.data.entity.FileEntity;
import com.example.rss.data.entity.ItemEntity;
import com.example.rss.data.repository.datasource.IDataStore;

import java.io.InputStream;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

public class DatabaseDataStore implements IDataStore {

    private final AppDatabase appDatabase;
    private final Cache cache;

    public DatabaseDataStore(AppDatabase appDatabase, Cache cache) {
        this.appDatabase = appDatabase;
        this.cache = cache;
    }

    @Override
    public Maybe<Long> addChannel(ChannelEntity channel) {
        return appDatabase.channelDAO().insert(ChannelDatabaseMapper.transform(channel));
    }

    @Override
    public Maybe<ChannelEntity> getChannelById(Long id) {
        return appDatabase.channelDAO().getChannelById(id).map(ChannelDatabaseMapper::transform).doOnSuccess(this.cache::put);
    }

    @Override
    public Maybe<List<ItemEntity>> getItemsByChannelId(Long id) {
        return appDatabase.itemDAO().getItemsByChannelId(id).map(ChannelDatabaseMapper::transformItemsDtoToEntity);
    }

    @Override
    public Maybe<List<ItemEntity>> getAllItems() {
        return appDatabase.itemDAO().getAllItems().map(ChannelDatabaseMapper::transformItemsDtoToEntity);
    }

    @Override
    public Maybe<List<Long>> InsertManyItems(List<ItemEntity> itemEntities) {
        return appDatabase.itemDAO().insertAll(ChannelDatabaseMapper.transformItems(itemEntities));
    }

    @Override
    public Maybe<ItemEntity> getItemByUniqueId(String hash) {
        return appDatabase.itemDAO().getItemByUniqueId(hash).map(ChannelDatabaseMapper::transform);
    }

    @Override
    public Maybe<Integer> deleteAllItems() {
        return appDatabase.itemDAO().deleteAllItems();
    }

    @Override
    public Maybe<Integer> deleteItemsByChannelId(Long id) {
        return appDatabase.itemDAO().deleteItemsByChannelId(id);
    }

    @Override
    public Maybe<Integer> deleteItemById(Long id) {
        return appDatabase.itemDAO().deleteItemById(id);
    }

    @Override
    public Maybe<Integer> updateReadById(Long id, Boolean isRead) {
        return appDatabase.itemDAO().updateReadById(id, isRead);
    }

    @Override
    public Maybe<Integer> getCountItemsByChannel(Long id) {
        return appDatabase.itemDAO().getCountItemsByChannel(id);
    }

    @Override
    public Maybe<Integer> getPosItemInChannelQueue(Long channelId, Long itemId) {
        return appDatabase.itemDAO().getPosItemInChannelQueue(channelId, itemId);
    }

    @Override
    public Maybe<Integer> getCountItemsForChannel(Long channelId) {
        return appDatabase.itemDAO().getCountItemsForChannel(channelId);
    }

    @Override
    public Maybe<List<ItemEntity>> getItemsWithOffsetByChannel(Long channelId, Integer offset, Integer limit) {
        if(channelId > 0)
            return appDatabase.itemDAO().getItemsWithOffsetByChannel(channelId, offset, limit).map(ChannelDatabaseMapper::transformItemsDtoToEntity);
        else
            return appDatabase.itemDAO().getItemsWithOffset(offset, limit).map(ChannelDatabaseMapper::transformItemsDtoToEntity);
    }

    @Override
    public Maybe<List<ItemEntity>> getFavoritesWithOffset(Integer offset, Integer limit) {
        return appDatabase.itemDAO().getFavoritesWithOffset(offset, limit).map(ChannelDatabaseMapper::transformItemsDtoToEntity);
    }

    @Override
    public Maybe<Integer> setAllRead() {
        return appDatabase.itemDAO().setAllRead();
    }

    @Override
    public Maybe<Integer> setReadForChannel(Long id) {
        return appDatabase.itemDAO().setReadForChannel(id);
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
    public Maybe<Integer> updateChannel(ChannelEntity channelEntity) {
        return appDatabase.channelDAO().updateChannel(ChannelDatabaseMapper.transform(channelEntity)).doOnSuccess(integer -> cache.evictByEntityId(channelEntity.getId().toString()));
    }

    @Override
    public Maybe<Integer> deleteAllChannels() {
        return appDatabase.channelDAO().deleteAllChannels();
    }

    @Override
    public Maybe<Integer> deleteChannelById(Long id) {
        return appDatabase.channelDAO().deleteById(id);
    }

    @Override
    public Maybe<Long> addFile(FileEntity fileEntity) {
        return appDatabase.fileDAO().insert(ChannelDatabaseMapper.transform(fileEntity));
    }

    @Override
    public Maybe<FileEntity> getFileById(Long id) {
        return appDatabase.fileDAO().getFileById(id).map(ChannelDatabaseMapper::transform);
    }

    @Override
    public Maybe<Integer> deleteFileById(Long id) {
        return appDatabase.fileDAO().deleteFileById(id);
    }

    @Override
    public Maybe<List<CategoryEntity>> getCategoriesByType(String mType) {
        return appDatabase.categoryDAO().getCategoriesByType(mType).map(ChannelDatabaseMapper::transformCategories);
    }

    @Override
    public Maybe<CategoryEntity> getCategoryById(Long id) {
        return appDatabase.categoryDAO().getCategoryById(id).map(ChannelDatabaseMapper::transform).doOnSuccess(cache::put);
    }

    @Override
    public Maybe<Long> addCategory(CategoryEntity categoryEntity) {
        return appDatabase.categoryDAO().addCategory(ChannelDatabaseMapper.transform(categoryEntity));
    }

    @Override
    public Maybe<Integer> updateNextExec(Long channelId, Long nextTimestamp) {
        return appDatabase.channelDAO().updateNextExec(channelId, nextTimestamp).doOnSuccess(integer -> cache.evictByEntityId(channelId.toString()));
    }

    @Override
    public Maybe<Integer> deleteFavByItemBy(Long id) {
        return appDatabase.favoriteDAO().deleteByItemBy(id);
    }

    @Override
    public Maybe<Long> insertFavorite(FavoriteEntity favoriteEntity) {
        return appDatabase.favoriteDAO().insert(ChannelDatabaseMapper.transform(favoriteEntity));
    }

    @Override
    public Maybe<Integer> deleteAllFavorites() {
        return appDatabase.favoriteDAO().deleteAll();
    }

    @Override
    public Maybe<InputStream> getRssFeedContent(String path) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }
}
