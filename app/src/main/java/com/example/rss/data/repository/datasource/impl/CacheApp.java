package com.example.rss.data.repository.datasource.impl;

import com.example.rss.data.entity.ChannelEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Singleton
public class CacheApp implements ICacheApp {

    @Inject
    public CacheApp() {
    }

    public Boolean isExpired(){
        return false;
    }

    public Boolean isCachedChannel(Long id)
    {
        return false;
    }

    @Override
    public Maybe<ChannelEntity> getChannel(Long id) {
        return Maybe.create(emitter -> emitter.onSuccess(new ChannelEntity()));
    }
}
