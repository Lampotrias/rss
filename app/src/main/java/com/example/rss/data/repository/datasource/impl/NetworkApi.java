package com.example.rss.data.repository.datasource.impl;

import com.example.rss.data.repository.datasource.IDataStore;

import io.reactivex.Single;
import okhttp3.OkHttpClient;

public class NetworkApi implements IDataStore {

    final OkHttpClient client;

    public NetworkApi(OkHttpClient client) {
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
}
