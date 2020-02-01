package com.example.rss.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.rss.data.database.dto.ChannelDTO;

import org.intellij.lang.annotations.Flow;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface ChannelDAO {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	Single<Long> insert(ChannelDTO channelDTO);

	@Query("SELECT * FROM channel WHERE source_link = :url")
	Single<ChannelDTO> getChannelByUrl(String url);

	@Query("SELECT * FROM channel WHERE id = :id")
	Single<ChannelDTO> getChannelById(Long id);

	@Query("SELECT * FROM channel")
	Flowable<List<ChannelDTO>> getAllChannels();
}
