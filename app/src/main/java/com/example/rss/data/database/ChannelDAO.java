package com.example.rss.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import io.reactivex.Single;

@Dao
public interface ChannelDAO {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	Single<Long> insert(ChannelDTO channelDTO);
}
