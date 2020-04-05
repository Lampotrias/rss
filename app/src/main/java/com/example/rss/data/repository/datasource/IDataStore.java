package com.example.rss.data.repository.datasource;

import com.example.rss.data.entity.CategoryEntity;
import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.FavoriteEntity;
import com.example.rss.data.entity.FileEntity;
import com.example.rss.data.entity.ItemEntity;

import java.io.InputStream;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

public interface IDataStore {
	//Network
	Maybe<InputStream> getRssFeedContent(String path);

	//Channel
	Maybe<Long> addChannel(ChannelEntity channel);
	Maybe<ChannelEntity> getChannelById(Long id);
	Single<ChannelEntity> getChannelByUrl(String url);
	Maybe<List<ChannelEntity>> getAllChannels();
	Maybe<Integer> updateChannel(ChannelEntity channelEntity);
	Maybe<Integer> deleteAllChannels();
	Maybe<Integer> deleteChannelById(Long id);
	Maybe<Integer> updateNextExec(Long channelId, Long nextTimestamp);

	//Items
	Maybe<List<ItemEntity>> getItemsByChannelId(Long id);
	Maybe<List<ItemEntity>> getAllItems();
	Maybe<List<Long>> InsertManyItems(List<ItemEntity> itemEntities);
	Maybe<ItemEntity> getItemByUniqueId(String hash);
	Maybe<Integer> deleteAllItems();
	Maybe<Integer> deleteItemsByChannelId(Long id);
	Maybe<Integer> deleteItemById(Long id);
	Maybe<Integer> updateReadById(Long id, Boolean isRead);
	Maybe<Integer> getCountItemsByChannel(Long id);
	Maybe<Integer> getPosItemInChannelQueue(Long channelId, Long itemId);
	Maybe<Integer> getCountItemsForChannel(Long channelId);
	Maybe<List<ItemEntity>> getItemsWithOffsetByChannel(Long channelId, Integer offset, Integer limit);
	Maybe<List<ItemEntity>> getFavoritesWithOffset(Integer offset, Integer limit);

	Maybe<Integer> setAllRead();
	Maybe<Integer> setReadForChannel(Long id);

	//Files
	Maybe<Long> addFile (FileEntity fileEntity);
	Maybe<FileEntity> getFileById(Long id);
	Maybe<Integer> deleteFileById(Long id);

	//Category
	Maybe<List<CategoryEntity>> getCategoriesByType(String mType);
	Maybe<CategoryEntity> getCategoryById(Long id);
	Maybe<Long> addCategory(CategoryEntity categoryEntity);
	Maybe<Integer> deleteCategoryById(Long id);
	Maybe<Integer> updateCategoryNameById(Long id, String name);

    //Favorite
	Maybe<Integer> deleteFavByItemBy(Long id);
	Maybe<Long> insertFavorite(FavoriteEntity favoriteEntity);
	Maybe<Integer> deleteAllFavorites();
}
