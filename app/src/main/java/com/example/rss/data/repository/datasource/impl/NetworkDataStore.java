package com.example.rss.data.repository.datasource.impl;

import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.FileEntity;
import com.example.rss.data.entity.RowEntity;
import com.example.rss.data.repository.datasource.IDataStore;
import com.example.rss.domain.xml.XmlParser;

import java.util.List;
import java.util.Objects;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkDataStore implements IDataStore {

    final OkHttpClient client;

    public NetworkDataStore(OkHttpClient client) {
        this.client = client;
    }

    @Override
    public Single<String> getRssFeedContent(String path) {
        return Single.create(emitter -> {

            Request request = new Request.Builder()
                    .url(path)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                emitter.onSuccess(Objects.requireNonNull(response.body()).string());
            }else{
                emitter.onError(new Throwable("Error connect to: path"));
            }

            /*if(!path.isEmpty()){
                emitter.onSuccess("Content");
            }else{
                emitter.onError(new Throwable("error"));
            }*/
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
    public Single<Long> addFile(FileEntity fileEntity) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }
}
