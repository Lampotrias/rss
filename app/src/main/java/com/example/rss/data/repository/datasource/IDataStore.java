package com.example.rss.data.repository.datasource;

import com.example.rss.data.entity.CategoryEntity;
import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.FileEntity;
import com.example.rss.data.entity.ItemEntity;
import com.example.rss.domain.File;

import java.io.InputStream;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface IDataStore {
	//Network
	Single<InputStream> getRssFeedContent(String path);

	//Channel
	Single<Long> addChannel(ChannelEntity channel);
	Single<ChannelEntity> getChannelById(Long id);
	Single<ChannelEntity> getChannelByUrl(String url);
	Flowable<List<ChannelEntity>> getAllChannels();

	//Items
	Maybe<List<ItemEntity>> getItemsByChannelId(Long id);
	Completable InsertManyItems(List<ItemEntity> itemEntities);

	//Files
	Single<Long> addFile (FileEntity fileEntity);
	Flowable<FileEntity> getFileById(Long id);

	//Category
	Flowable<List<CategoryEntity>> getCategoriesByType(String mType);
}
