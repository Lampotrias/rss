package com.example.rss.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.rss.data.database.Converters.ConverterDate;
import com.example.rss.data.database.dto.ChannelDTO;

@Database(entities = {ChannelDTO.class}, version = 1, exportSchema = false)
@TypeConverters({ConverterDate.class})
public abstract class AppDatabase extends RoomDatabase {
	private static AppDatabase singleton;
	private static final String DATABASE_NAME = "rss.db";

	public abstract ChannelDAO channelDAO();

	public static AppDatabase getInstance(Context context){
		if (singleton == null){
			synchronized (AppDatabase.class) {
				if (singleton == null){
					singleton = Room.databaseBuilder(context,
							AppDatabase.class,
							DATABASE_NAME)
							.fallbackToDestructiveMigration()
							//.addMigrations(MIGRATION_2_3)
							//.allowMainThreadQueries()
							.build();
				}
			}
		}
		return singleton;
	}
}
