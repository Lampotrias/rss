package com.example.rss.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.rss.data.database.dto.ItemDTO;
import com.example.rss.domain.Item;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface ItemDAO {
    @Query("SELECT item.id, item.channel_id, item.guid, item.title, item.description, item.link,  item.pub_date, item.enclosure, item.is_read, favorite.item_id as is_favorite FROM item LEFT JOIN favorite WHERE item.channel_id = :id ORDER BY item.pub_date DESC")
    Maybe<List<ItemDTO>> getItemsByChannelId(Long id);

    @Query("SELECT * FROM item")
    Maybe<List<ItemDTO>> getAllItems();

    @Insert
    Maybe<List<Long>> insertAll(List<ItemDTO> itemDTOS);

    @Query("SELECT * FROM item where guid = :hash")
    Single<ItemDTO> getItemByUniqueId(String hash);

    @Query("DELETE FROM item")
    Completable deleteAllItems();
}
