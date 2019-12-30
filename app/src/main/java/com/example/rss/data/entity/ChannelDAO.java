package com.example.rss.data.entity;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface ChannelDAO {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	Single<Long> insert(ChannelDTO channelDTO);
}
