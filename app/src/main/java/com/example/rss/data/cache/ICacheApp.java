package com.example.rss.data.cache;

import com.example.rss.data.entity.ChannelEntity;

import io.reactivex.Maybe;
import io.reactivex.Single;

public interface ICacheApp {
    Boolean isExpired();
    Boolean isCachedChannel(Long id);
    Maybe<ChannelEntity> getChannelById(Long id);
    void putChannel(ChannelEntity channelEntity);
}
