package com.example.rss.data.repository.datasource.impl;

import com.example.rss.data.entity.ChannelEntity;

import io.reactivex.Single;

public interface ICacheApp {
    Boolean isExpired();
    Boolean isCachedChannel(Long id);
    Single<ChannelEntity> getChannel(Long id);
}
