package com.example.rss.data.repository.datasource.impl;

import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.RowEntity;
import com.example.rss.data.repository.datasource.IDataStore;

import java.util.List;

import io.reactivex.Single;
import okhttp3.OkHttpClient;

public class NetworkDataStore implements IDataStore {

    final OkHttpClient client;

    public NetworkDataStore(OkHttpClient client) {
        this.client = client;
    }

    @Override
    public Single<String> getRssFeedContent(String path) {
        return Single.create(emitter -> {
            if(!path.isEmpty()){
                emitter.onSuccess("Content");
            }else{
                emitter.onError(new Throwable("error"));
            }
        });
    }

    @Override
    public Single<Long> addChannel(ChannelEntity channel) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Single<ChannelEntity> getChannelById(Integer id) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Single<List<RowEntity>> getRowsByChannelId(Integer id) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }
}
