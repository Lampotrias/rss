package com.example.rss.data.repository.datasource.impl;

import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.RowEntity;
import com.example.rss.data.repository.datasource.IDataStore;
import com.example.rss.domain.Channel;

import java.util.List;

import io.reactivex.Single;

public class DiskDataStore implements IDataStore {
    private final ICacheApp cache;

    public DiskDataStore(ICacheApp cache) {
        this.cache = cache;
    }

    @Override
    public Single<Long> addChannel(ChannelEntity channel) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Single<String> getRssFeedContent(String path) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Single<ChannelEntity> getChannelById(Integer id) {
        return cache.getChannel(id);
    }

    @Override
    public Single<List<RowEntity>> getRowsByChannelId(Integer id) {
        return null;
    }
}
