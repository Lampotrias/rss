package com.example.rss.data.repository.datasource;

import com.example.rss.data.entity.CategoryEntity;
import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.FileEntity;
import com.example.rss.data.entity.ItemEntity;

import java.io.InputStream;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface IDataStore {
	Single<Long> addChannel(ChannelEntity channel);
	Single<InputStream> getRssFeedContent(String path);
	Single<ChannelEntity> getChannelById(Long id);
	Single<List<ItemEntity>> getItemsByChannelId(Long id);

	Single<ChannelEntity> getChannelByUrl(String url);
	Flowable<List<ChannelEntity>> getAllChannels();
	Single<Long> addFile (FileEntity fileEntity);

	Flowable<List<CategoryEntity>> getCategoriesByType(String mType);
}
