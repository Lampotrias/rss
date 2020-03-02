package com.example.rss.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.rss.data.database.dto.FavoritesDTO;

import io.reactivex.Completable;

@Dao
public interface FavoriteDAO {
    @Query("DELETE FROM favorite WHERE item_id =:id")
    Completable deleteByItemBy(Long id);

    @Query("DELETE FROM favorite")
    Completable deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(FavoritesDTO favoritesDTO);
}
