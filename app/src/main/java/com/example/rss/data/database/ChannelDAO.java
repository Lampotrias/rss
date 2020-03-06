package com.example.rss.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.rss.data.database.dto.ChannelDTO;
import java.util.List;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface ChannelDAO {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	Maybe<Long> insert(ChannelDTO channelDTO);

	@Query("SELECT * FROM channel WHERE source_link = :url")
	Single<ChannelDTO> getChannelByUrl(String url);

	@Query("SELECT * FROM channel WHERE id = :id")
	Maybe<ChannelDTO> getChannelById(Long id);

	@Query("SELECT * FROM channel")
	Maybe<List<ChannelDTO>> getAllChannels();

	@Query("UPDATE channel set next_sync_date = :nextTimestamp WHERE id = :channelId")
	Maybe<Integer> updateNextExec(Long channelId, Long nextTimestamp);

	@Update
	Single<Integer> updateChannel(ChannelDTO channelDTO);

	@Query("DELETE FROM channel")
	Maybe<Integer> deleteAllChannels();

	@Query("DELETE FROM channel WHERE id = :id")
	Maybe<Integer> deleteById(Long id);
}
