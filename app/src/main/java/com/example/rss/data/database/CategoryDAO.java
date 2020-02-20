package com.example.rss.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.rss.data.database.dto.CategoryDTO;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface CategoryDAO {
    @Query("SELECT * FROM category WHERE type = :type OR type = ''")
    Maybe<List<CategoryDTO>> getCategoriesByType(String type);

    @Insert @Transaction
    Completable insert(CategoryDTO categoryDTO);

    @Query("SELECT * FROM category WHERE id = :id")
    Maybe<CategoryDTO> getCategoryById(Long id);

    @Insert
    Maybe<Long> addCategory(CategoryDTO category);

}