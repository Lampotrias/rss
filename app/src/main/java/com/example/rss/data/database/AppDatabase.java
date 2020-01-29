package com.example.rss.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.rss.data.database.dto.CategoryDTO;
import com.example.rss.data.database.dto.FavoritesDTO;
import com.example.rss.data.database.dto.FileDTO;
import com.example.rss.data.database.dto.ItemDTO;
import com.example.rss.data.database.сonverters.ConverterDate;
import com.example.rss.data.database.dto.ChannelDTO;

@Database(entities = {ChannelDTO.class, FileDTO.class, FavoritesDTO.class, ItemDTO.class, CategoryDTO.class}, version = 8, exportSchema = false)
@TypeConverters({ConverterDate.class})
public abstract class AppDatabase extends RoomDatabase {
	private static AppDatabase singleton;
	private static final String DATABASE_NAME = "rss.db";

	public abstract FileDAO fileDAO();
	public abstract ChannelDAO channelDAO();
	public abstract CategoryDAO categoryDAO();

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
