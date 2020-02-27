package com.example.rss.data.cache;

import com.example.rss.data.cache.ICacheApp;
import com.example.rss.data.entity.ChannelEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Maybe;

@Singleton
public class CacheAppChannel implements ICacheApp<ChannelEntity> {

    public CacheAppChannel() {
    }

    public Boolean isExpired(){
        return false;
    }

    public Boolean isCached(Long id)
    {
        return false;
    }

    @Override
    public Maybe getByEntityId(Long id) {
        return null;
    }

    @Override
    public void putEntity(ChannelEntity channelEntity) {

    }
}
