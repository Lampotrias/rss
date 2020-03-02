package com.example.rss.domain.repositories;

import com.example.rss.domain.Category;
import com.example.rss.domain.Channel;
import com.example.rss.domain.Favorite;
import com.example.rss.domain.File;
import com.example.rss.domain.Item;

import java.io.InputStream;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface IRepository {
	//Network only
	Maybe<InputStream> getRssFeedContent(String path);

	//Channel
	Maybe<Long> addChannel(Channel channel);
	Maybe<Channel> getChannelById(Long id);
	Maybe<List<Channel>> getAllChannels();
	Single<Channel> getChannelByUrl(String url);
	Single<Integer> updateChannel(Channel channel);
	Completable deleteAllChannels();
	Maybe<Integer> updateNextExec(Long channelId, Long nextTimestamp);

	//Items
	Maybe<List<Item>> getItemsByChannelId(Long id);
	Maybe<List<Item>> getAllItems();
	Maybe<List<Long>> InsertManyItems(List<Item> items);
	Single<Item> getItemByUniqueId(String hash);
	Completable deleteAllItems();
	Completable updateReadById(Long id, Boolean isRead);

	//Category
	Maybe<List<Category>> getCategoriesByType(String mType);
	Maybe<Category> getCategoryById(Long id);
	Maybe<Long> addCategory(Category category);

	//File
	Single<Long> addFile(File file);
	Maybe<File> getFileById(Long id);

    //Favorite
	Completable deleteFavByItemBy(Long id);
	Completable insertFavorite(Favorite favorite);
	Completable deleteAllFavorites();
}
