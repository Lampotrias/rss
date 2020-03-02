package com.example.rss.data.repository.datasource.impl;

import com.example.rss.data.cache.Cache;
import com.example.rss.data.entity.CategoryEntity;
import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.FileEntity;
import com.example.rss.data.entity.ItemEntity;
import com.example.rss.data.repository.datasource.IDataStore;

import java.io.InputStream;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class DiskDataStore implements IDataStore {
    private final Cache cache;

    public DiskDataStore(Cache cache) {
        this.cache = cache;
    }

    @Override
    public Maybe<Long> addChannel(ChannelEntity channel) {
        //return this.cache.putChannel(channel);
        return null;
    }

    @Override
    public Maybe<InputStream> getRssFeedContent(String path) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<ChannelEntity> getChannelById(Long id) {
        return cache.get(id);
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
    public Single<ItemEntity> getItemByUniqueId(String hash) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Completable deleteAllItems() {
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
    public Single<Integer> updateChannel(ChannelEntity channelEntity) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Completable deleteAllChannels() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Single<Long> addFile(FileEntity fileEntity) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<FileEntity> getFileById(Long id) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<List<CategoryEntity>> getCategoriesByType(String mType) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<CategoryEntity> getCategoryById(Long id) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<Long> addCategory(CategoryEntity categoryEntity) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<Integer> updateNextExec(Long channelId, Long nextTimestamp) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }
}
