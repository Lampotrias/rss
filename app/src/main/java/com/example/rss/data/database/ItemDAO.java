package com.example.rss.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.rss.data.database.dto.ItemDTO;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Maybe;

@Dao
public interface ItemDAO {
    @Query("SELECT item.id, item.channel_id, item.guid, item.title, item.description, item.link,  item.pub_date, item.enclosure, item.is_read, CASE WHEN favorite.item_id > 0 THEN 1 END is_favorite FROM item LEFT JOIN favorite on item.id = favorite.item_id WHERE item.channel_id = :id ORDER BY item.pub_date DESC")
    Maybe<List<ItemDTO>> getItemsByChannelId(Long id);

    @Query("SELECT item.id, item.channel_id, item.guid, item.title, item.description, item.link,  item.pub_date, item.enclosure, item.is_read, CASE WHEN favorite.item_id > 0 THEN 1 END is_favorite FROM item LEFT JOIN favorite on item.id = favorite.item_id ORDER BY item.pub_date DESC")
    Maybe<List<ItemDTO>> getAllItems();

    @Insert
    Maybe<List<Long>> insertAll(List<ItemDTO> itemDTOS);

    @Query("SELECT * FROM item where guid = :hash")
    Maybe<ItemDTO> getItemByUniqueId(String hash);

    @Query("DELETE FROM item")
    Completable deleteAllItems();

    @Query("UPDATE item set is_read = :isRead WHERE id = :id")
    Completable updateReadById(Long id, Boolean isRead);
}
