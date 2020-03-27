package com.example.rss.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.rss.data.database.dto.FileDTO;

import io.reactivex.Maybe;

@Dao
public interface FileDAO {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	Maybe<Long> insert(FileDTO fileDTO);

	@Query("SELECT * FROM file WHERE id = :id")
	Maybe<FileDTO> getFileById(Long id);

	@Query("DELETE FROM file WHERE id = :id")
	Maybe<Integer> deleteFileById(Long id);
}
