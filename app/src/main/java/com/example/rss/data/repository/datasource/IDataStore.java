package com.example.rss.data.repository.datasource;

import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.RowEntity;

import java.util.List;

import io.reactivex.Single;

public interface IDataStore {
	Single<Long> addChannel(ChannelEntity channel);
	Single<String> getRssFeedContent(String path);
	Single<ChannelEntity> getChannelById(Integer id);
	Single<List<RowEntity>> getRowsByChannelId(Integer id);
}
