package com.example.rss.data.entity.mapper;

import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.FileEntity;
import com.example.rss.domain.Channel;
import com.example.rss.domain.File;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RepositoryEntityDataMapper {

    @Inject
    public RepositoryEntityDataMapper() {
    }

    //ToDO доделать
    public ChannelEntity transform (Channel channel){
        ChannelEntity channelEntity = null;
        if (channel != null) {
            channelEntity = new ChannelEntity();
            channelEntity.setChannelId(channel.getChannelId());
            channelEntity.setTitle(channel.getTitle());
            channelEntity.setFileId(channel.getFileId());
            channelEntity.setDescription(channel.getDescription());
            channelEntity.setLink(channel.getLink());
            channelEntity.setCategoryId(channel.getCategoryId());
            channelEntity.setCacheImage(channel.getCacheImage());
            channelEntity.setDownloadFullText(channel.getDownloadFullText());
            channelEntity.setOnlyWifi(channel.getOnlyWifi());
            channelEntity.setLastBuild(channel.getLastBuild());
            channelEntity.setNextSyncDate(channel.getNextSyncDate());
        }
        return channelEntity;
    }

    public Channel transform(ChannelEntity channelEntity){
        Channel channel = null;

        if (channelEntity != null){
            channel = new Channel();
            channel.setChannelId(channelEntity.getChannelId());
            channel.setTitle(channelEntity.getTitle());
            channel.setFileId(channelEntity.getFileId());
            channel.setLink(channelEntity.getLink());
            channel.setDescription(channelEntity.getDescription());
            channel.setCategoryId(channelEntity.getCategoryId());
            channel.setCacheImage(channelEntity.getCacheImage());
            channel.setDownloadFullText(channelEntity.getDownloadFullText());
            channel.setOnlyWifi(channelEntity.getOnlyWifi());
            channel.setLastBuild(channelEntity.getLastBuild());
            channel.setNextSyncDate(channelEntity.getNextSyncDate());
        }
        return channel;
    }

    public File transform (FileEntity fileEntity){
        File file = null;
        if (fileEntity != null) {
            file = new File();
            file.setFileId(fileEntity.getFileId());
            file.setPath(fileEntity.getPath());
            file.setDescription((fileEntity.getDescription()));
            file.setExternal(fileEntity.getExternal());
            file.setType(fileEntity.getType());
        }
        return file;
    }

    public FileEntity transform (File file){
        FileEntity fileEntity = null;
        if (file != null) {
            fileEntity = new FileEntity();
            fileEntity.setFileId(file.getFileId());
            fileEntity.setPath(file.getPath());
            fileEntity.setDescription((file.getDescription()));
            fileEntity.setExternal(file.getExternal());
            fileEntity.setType(file.getType());
        }
        return fileEntity;
    }

}
