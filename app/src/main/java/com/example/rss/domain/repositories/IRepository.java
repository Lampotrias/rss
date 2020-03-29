package com.example.rss.domain.repositories;

import com.example.rss.domain.Category;
import com.example.rss.domain.Channel;
import com.example.rss.domain.Favorite;
import com.example.rss.domain.File;
import com.example.rss.domain.Item;

import java.io.InputStream;
import java.util.List;

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
	Maybe<Integer> updateChannel(Channel channel);
	Maybe<Integer> deleteAllChannels();
	Maybe<Integer> deleteChannelById(Long id);
	Maybe<Integer> updateNextExec(Long channelId, Long nextTimestamp);

	//Items
	Maybe<List<Item>> getItemsByChannelId(Long id);
	Maybe<List<Item>> getAllItems();
	Maybe<List<Long>> insertManyItems(List<Item> items);
	Maybe<Item> getItemByUniqueId(String hash);
	Maybe<Integer> deleteAllItems();
	Maybe<Integer> deleteItemById(Long id);
	Maybe<Integer> deleteItemsByChannelId(Long id);
	Maybe<Integer> updateReadById(Long id, Boolean isRead);
	Maybe<Integer> getCountItemsByChannel(Long id);
	Maybe<Integer> getPosItemInChannelQueue(Long channelId, Long itemId);
	Maybe<Integer> getCountItemsForChannel(Long channelId);
	Maybe<List<Item>> getItemsWithOffsetByChannel(Long channelId, Integer offset, Integer limit);
	Maybe<List<Item>> getFavoritesWithOffset(Integer offset, Integer limit);

	Maybe<Integer> setAllRead();
	Maybe<Integer> setReadForChannel(Long id);

	//Category
	Maybe<List<Category>> getCategoriesByType(String mType);
	Maybe<Category> getCategoryById(Long id);
	Maybe<Long> addCategory(Category category);

	//File
	Maybe<Long> addFile(File file);
	Maybe<File> getFileById(Long id);
	Maybe<Integer> deleteFileById(Long id);
    //Favorite
	Maybe<Integer> deleteFavByItemBy(Long id);
	Maybe<Long> insertFavorite(Favorite favorite);
	Maybe<Integer> deleteAllFavorites();
}
