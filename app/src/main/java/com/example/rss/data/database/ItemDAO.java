package com.example.rss.data.database;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.rss.data.database.dto.ItemDTO;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ItemDAO {
    @Query("SELECT item.*, (SELECT 1 FROM favorite WHERE favorite.item_id = item.id) as is_favorite FROM item, favorite WHERE item.channel_id=:id ORDER BY item.id DESC")
    Flowable<List<ItemDTO>> getItemsByChannelId(Long id);
}
