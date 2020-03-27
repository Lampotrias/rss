package com.example.rss.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.rss.data.database.dto.ItemDTO;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface ItemDAO {
    @Query("SELECT item.id, item.channel_id, item.guid, item.title, item.description, item.link,  item.pub_date, item.enclosure, item.is_read, CASE WHEN favorite.item_id > 0 THEN 1 END is_favorite FROM item LEFT JOIN favorite on item.id = favorite.item_id WHERE item.channel_id = :id ORDER BY item.pub_date DESC")
    Maybe<List<ItemDTO>> getItemsByChannelId(Long id);

    @Query("SELECT item.id, item.channel_id, item.guid, item.title, item.description, item.link,  item.pub_date, item.enclosure, item.is_read, CASE WHEN favorite.item_id > 0 THEN 1 END is_favorite FROM item LEFT JOIN favorite on item.id = favorite.item_id ORDER BY item.pub_date DESC")
    Maybe<List<ItemDTO>> getAllItems();

    @Query("SELECT COUNT(*) FROM item WHERE channel_id = :channelId")
    Maybe<Integer> getCountItemsForChannel(Long channelId);

    @Query("SELECT COUNT(*) FROM item i1 WHERE i1.channel_id = :channelId AND i1.pub_date >= (SELECT i2.pub_date FROM item i2 WHERE i2.id = :itemId) ORDER BY i1.pub_date DESC")
    Maybe<Integer> getPosItemInChannelQueue(Long channelId, Long itemId);

    @Query("SELECT item.id, item.channel_id, item.guid, item.title, item.description, item.link,  item.pub_date, item.enclosure, item.is_read, CASE WHEN favorite.item_id > 0 THEN 1 END is_favorite FROM item LEFT JOIN favorite on item.id = favorite.item_id WHERE channel_id = :channelId ORDER BY pub_date DESC LIMIT :offset, :limit")
    Maybe<List<ItemDTO>> getItemsWithOffsetByChannel(Long channelId, Integer offset, Integer limit);

    @Query("SELECT item.id, item.channel_id, item.guid, item.title, item.description, item.link,  item.pub_date, item.enclosure, item.is_read, CASE WHEN favorite.item_id > 0 THEN 1 END is_favorite FROM item LEFT JOIN favorite on item.id = favorite.item_id ORDER BY pub_date DESC LIMIT :offset, :limit")
    Maybe<List<ItemDTO>> getItemsWithOffset(Integer offset, Integer limit);

    @Query("SELECT item.id, item.channel_id, item.guid, item.title, item.description, item.link,  item.pub_date, item.enclosure, item.is_read, CASE WHEN favorite.item_id > 0 THEN 1 END is_favorite FROM favorite INNER JOIN item on item.id = favorite.item_id ORDER BY pub_date DESC LIMIT :offset, :limit")
    Maybe<List<ItemDTO>> getFavoritesWithOffset(Integer offset, Integer limit);

    @Insert
    Maybe<List<Long>> insertAll(List<ItemDTO> itemDTOS);

    @Query("SELECT * FROM item WHERE guid = :hash")
    Maybe<ItemDTO> getItemByUniqueId(String hash);

    @Query("DELETE FROM item")
    Maybe<Integer> deleteAllItems();

    @Query("DELETE FROM item WHERE id = :id")
    Maybe<Integer> deleteItemById(Long id);

    @Query("DELETE FROM item WHERE channel_id = :id")
    Maybe<Integer> deleteItemsByChannelId(Long id);

    @Query("UPDATE item SET is_read = :isRead WHERE id = :id")
    Maybe<Integer> updateReadById(Long id, Boolean isRead);

    @Query("SELECT COUNT(*) FROM item WHERE channel_id = :id")
    Maybe<Integer> getCountItemsByChannel(Long id);
}
