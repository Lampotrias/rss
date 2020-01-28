package com.example.rss.data.repository.datasource.impl;

import com.example.rss.data.database.dto.ChannelDTO;
import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.FileEntity;
import com.example.rss.data.entity.RowEntity;
import com.example.rss.data.exception.NetworkConnectionException;
import com.example.rss.data.repository.datasource.IDataStore;

import java.io.InputStream;
import java.net.URL;
import java.util.List;


import io.reactivex.Single;

public class NetworkDataStore implements IDataStore {

    public NetworkDataStore() {

    }

    @Override
    public Single<InputStream> getRssFeedContent(String path) {
        return Single.create(emitter -> {
            InputStream stream;
            try {
                stream = new URL(path).openStream();
                emitter.onSuccess(stream);
            }catch (Exception e){
                emitter.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Single<Long> addChannel(ChannelEntity channel) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Single<ChannelEntity> getChannelById(Long id) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Single<List<RowEntity>> getRowsByChannelId(Long id) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Single<ChannelEntity> getChannelByUrl(String url) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Single<Long> addFile(FileEntity fileEntity) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }
}
