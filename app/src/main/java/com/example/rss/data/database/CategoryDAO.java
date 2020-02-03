package com.example.rss.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.rss.data.database.dto.CategoryDTO;

import org.intellij.lang.annotations.Flow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface CategoryDAO {
    @Query("SELECT * FROM category WHERE type = :type OR type = ''")
    Flowable<List<CategoryDTO>> getCategoriesByType(String type);

    @Insert @Transaction
    Completable insert(CategoryDTO categoryDTO);


}