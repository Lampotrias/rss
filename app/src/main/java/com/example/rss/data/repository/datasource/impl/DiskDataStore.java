package com.example.rss.data.repository.datasource.impl;

import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.FileEntity;
import com.example.rss.data.entity.ItemEntity;
import com.example.rss.data.repository.datasource.IDataStore;

import java.io.InputStream;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public abstract class DiskDataStore implements IDataStore {
    private final ICacheApp cache;

    public DiskDataStore(ICacheApp cache) {
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
    public Single<ChannelEntity> getChannelById(Long id) {
        return cache.getChannel(id);
    }

    @Override
    public Maybe<List<ItemEntity>> getItemsByChannelId(Long id) {
        return null;
    }

    @Override
    public Single<ChannelEntity> getChannelByUrl(String url) {
        return null;
    }

    @Override
    public Single<Long> addFile(FileEntity fileEntity) {
        return null;
    }
}
