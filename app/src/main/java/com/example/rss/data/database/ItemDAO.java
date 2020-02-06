package com.example.rss.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.rss.data.database.dto.ItemDTO;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface ItemDAO {
    @Query("SELECT item.id, item.channel_id, item.guid, item.title, item.description, item.link,  item.pub_date, item.enclosure, item.is_read, favorite.item_id as is_favorite FROM item LEFT JOIN favorite WHERE item.channel_id = :id ORDER BY item.id DESC")
    Maybe<List<ItemDTO>> getItemsByChannelId(Long id);

    @Insert
    Completable insertAll(List<ItemDTO> itemDTOS);
}
