package com.example.rss.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rss.data.database.dto.ChannelDTO;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface ChannelDAO {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	Maybe<Long> insert(ChannelDTO channelDTO);

	@Query("SELECT * FROM channel WHERE source_link = :url")
	Single<ChannelDTO> getChannelByUrl(String url);

	@Query("SELECT * FROM channel WHERE id = :id")
	Single<ChannelDTO> getChannelById(Long id);

	@Query("SELECT * FROM channel")
	Maybe<List<ChannelDTO>> getAllChannels();

	@Update
	Single<Integer> updateChannel(ChannelDTO channelDTO);

	@Query("DELETE FROM channel")
	Completable deleteAllChannels();
}
