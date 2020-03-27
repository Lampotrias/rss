package com.example.rss.data.repository.datasource.impl;

import com.example.rss.data.cache.Cache;
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

public class DiskDataStore implements IDataStore {
    private final Cache cache;

    public DiskDataStore(Cache cache) {
        this.cache = cache;
    }

    @Override
    public Maybe<Long> addChannel(ChannelEntity channel) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<InputStream> getRssFeedContent(String path) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<ChannelEntity> getChannelById(Long id) {
        return cache.get(id.toString());
    }

    @Override
    public Maybe<List<ItemEntity>> getItemsByChannelId(Long id) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<List<ItemEntity>> getAllItems() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<List<Long>> InsertManyItems(List<ItemEntity> itemEntities) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<ItemEntity> getItemByUniqueId(String hash) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<Integer> deleteAllItems() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<Integer> deleteItemsByChannelId(Long id) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<Integer> deleteItemById(Long id) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<Integer> updateReadById(Long id, Boolean isRead) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<Integer> getCountItemsByChannel(Long id) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<Integer> getPosItemInChannelQueue(Long channelId, Long itemId) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<Integer> getCountItemsForChannel(Long channelId) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<List<ItemEntity>> getItemsWithOffsetByChannel(Long channelId, Integer offset, Integer limit) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<Long> addFile(FileEntity fileEntity) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Single<ChannelEntity> getChannelByUrl(String url) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<List<ChannelEntity>> getAllChannels() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<Integer> updateChannel(ChannelEntity channelEntity) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<Integer> deleteAllChannels() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<Integer> deleteChannelById(Long id) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<FileEntity> getFileById(Long id) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<Integer> deleteFileById(Long id) {
        return null;
    }

    @Override
    public Maybe<List<CategoryEntity>> getCategoriesByType(String mType) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<CategoryEntity> getCategoryById(Long id) {
        return cache.get(id.toString());
    }

    @Override
    public Maybe<Long> addCategory(CategoryEntity categoryEntity) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<Integer> deleteFavByItemBy(Long id) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<Long> insertFavorite(FavoriteEntity favoriteEntity) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<Integer> deleteAllFavorites() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<Integer> updateNextExec(Long channelId, Long nextTimestamp) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }
}
