package com.example.rss.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.rss.data.database.dto.FileDTO;

import io.reactivex.Single;

@Dao
public interface FileDAO {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	Single<Long> insert(FileDTO fileDTO);

	@Query("SELECT * FROM file WHERE id = :id")
	Single<FileDTO> getFileById(Long id);
}
