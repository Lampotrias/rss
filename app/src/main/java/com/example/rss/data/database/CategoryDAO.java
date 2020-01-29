package com.example.rss.data.database;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.rss.data.database.dto.CategoryDTO;

import org.intellij.lang.annotations.Flow;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface CategoryDAO {
    @Query("SELECT * FROM category WHERE type = :type")
    Flowable<List<CategoryDTO>> getCategoriesByType(String type);

}