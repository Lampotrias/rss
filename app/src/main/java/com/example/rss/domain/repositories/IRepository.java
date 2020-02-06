package com.example.rss.domain.repositories;

import com.example.rss.domain.Category;
import com.example.rss.domain.Channel;
import com.example.rss.domain.File;
import com.example.rss.domain.Item;

import java.io.InputStream;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface IRepository {
	//Network only
	Single<InputStream> getRssFeedContent(String path);

	//Channel
	Single<Long> addChannel(Channel channel);
	Single<Channel> getChannelById(Long id);
	Flowable<List<Channel>> getAllChannels();
	Single<Channel> getChannelByUrl(String url);

	//Items
	Maybe<List<Item>> getItemsByChannelId(Long id);
	Completable InsertManyItems(List<Item> items);

	//Category
	Flowable<List<Category>> getCategoriesByType(String mType);

	//File
	Single<Long> addFile(File file);
	Flowable<File> getFileById(Long id);
}
