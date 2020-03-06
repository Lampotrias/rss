package com.example.rss.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.rss.data.database.dto.FavoritesDTO;

import io.reactivex.Completable;
import io.reactivex.Maybe;

@Dao
public interface FavoriteDAO {
    @Query("DELETE FROM favorite WHERE item_id =:id")
    Maybe<Integer> deleteByItemBy(Long id);

    @Query("DELETE FROM favorite")
    Maybe<Integer> deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Maybe<Long> insert(FavoritesDTO favoritesDTO);
}
