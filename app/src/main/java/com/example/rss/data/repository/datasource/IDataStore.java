package com.example.rss.data.repository.datasource;

import com.example.rss.data.entity.CategoryEntity;
import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.FileEntity;
import com.example.rss.data.entity.ItemEntity;
import com.example.rss.domain.File;
import com.example.rss.domain.Item;

import java.io.InputStream;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface IDataStore {
	//Network
	Maybe<InputStream> getRssFeedContent(String path);

	//Channel
	Maybe<Long> addChannel(ChannelEntity channel);
	Single<ChannelEntity> getChannelById(Long id);
	Single<ChannelEntity> getChannelByUrl(String url);
	Maybe<List<ChannelEntity>> getAllChannels();

	//Items
	Maybe<List<ItemEntity>> getItemsByChannelId(Long id);
	Maybe<List<Long>> InsertManyItems(List<ItemEntity> itemEntities);
	Observable<ItemEntity> getItemByUniqueId(String hash);

	//Files
	Single<Long> addFile (FileEntity fileEntity);
	Flowable<FileEntity> getFileById(Long id);

	//Category
	Flowable<List<CategoryEntity>> getCategoriesByType(String mType);
}
