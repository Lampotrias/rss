package com.example.rss.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.rss.data.database.dto.ChannelDTO;

import io.reactivex.Single;

@Dao
public interface ChannelDAO {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	Single<Long> insert(ChannelDTO channelDTO);

	@Query("SELECT * FROM channel WHERE source_link = :url")
	Single<ChannelDTO> getChannelByUrl(String url);

	@Query("SELECT * FROM channel WHERE id = :id")
	Single<ChannelDTO> getChannelById(Long id);
}
