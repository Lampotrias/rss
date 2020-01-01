package com.example.rss.data.entity.mapper;

import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.domain.Channel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChannelEntityDataMapper {

    @Inject
    public ChannelEntityDataMapper() {
    }

    public ChannelEntity transform (Channel channel){
        return new ChannelEntity();
    }

    public Channel transform(ChannelEntity channelEntity){
        return new Channel();
    }
}
