package com.example.rss.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.rss.data.database.dto.FileDTO;

import io.reactivex.Single;

@Dao
public interface FileDAO {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	Single<Long> insert(FileDTO fileDTO);
}
