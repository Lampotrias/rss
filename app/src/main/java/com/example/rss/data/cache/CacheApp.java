package com.example.rss.data.cache;

import com.example.rss.data.cache.ICacheApp;
import com.example.rss.data.entity.ChannelEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Maybe;

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
    public Maybe<ChannelEntity> getChannelById(Long id) {
        return Maybe.create(emitter -> emitter.onSuccess(new ChannelEntity()));
    }

    @Override
    public void putChannel(ChannelEntity channelEntity) {
        if (channelEntity != null)

    }
}
