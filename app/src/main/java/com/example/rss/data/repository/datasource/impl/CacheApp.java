package com.example.rss.data.repository.datasource.impl;

import com.example.rss.data.entity.ChannelEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class CacheApp implements ICacheApp {

    @Inject
    public CacheApp() {
    }

    public Boolean isExpired(){
        return false;
    }

    public Boolean isCachedChannel(Integer id)
    {
        return false;
    }

    @Override
    public Single<ChannelEntity> getChannel(Integer id) {
        return Single.create(emitter -> emitter.onSuccess(new ChannelEntity()));
    }
}
