package com.example.rss.data.repository.datasource;

import com.example.rss.data.entity.CategoryEntity;
import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.FileEntity;
import com.example.rss.data.entity.ItemEntity;
import com.example.rss.domain.Category;
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
	Maybe<ChannelEntity> getChannelById(Long id);
	Single<ChannelEntity> getChannelByUrl(String url);
	Maybe<List<ChannelEntity>> getAllChannels();
	Single<Integer> updateChannel(ChannelEntity channelEntity);
	Completable deleteAllChannels();

	//Items
	Maybe<List<ItemEntity>> getItemsByChannelId(Long id);
	Maybe<List<ItemEntity>> getAllItems();
	Maybe<List<Long>> InsertManyItems(List<ItemEntity> itemEntities);
	Single<ItemEntity> getItemByUniqueId(String hash);
	Completable deleteAllItems();

	//Files
	Single<Long> addFile (FileEntity fileEntity);
	Maybe<FileEntity> getFileById(Long id);

	//Category
	Maybe<List<CategoryEntity>> getCategoriesByType(String mType);
	Maybe<CategoryEntity> getCategoryById(Long id);
	Maybe<Long> addCategory(CategoryEntity categoryEntity);

    Maybe<Integer> updateNextExec(Long channelId, Long nextTimestamp);
}
