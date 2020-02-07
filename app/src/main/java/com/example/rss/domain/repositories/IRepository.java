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
import io.reactivex.Observable;
import io.reactivex.Single;

public interface IRepository {
	//Network only
	Maybe<InputStream> getRssFeedContent(String path);

	//Channel
	Maybe<Long> addChannel(Channel channel);
	Single<Channel> getChannelById(Long id);
	Maybe<List<Channel>> getAllChannels();
	Single<Channel> getChannelByUrl(String url);

	//Items
	Maybe<List<Item>> getItemsByChannelId(Long id);
	Maybe<List<Long>> InsertManyItems(List<Item> items);
	Observable<Item> getItemByUniqueId(String hash);

	//Category
	Flowable<List<Category>> getCategoriesByType(String mType);

	//File
	Single<Long> addFile(File file);
	Flowable<File> getFileById(Long id);
}
