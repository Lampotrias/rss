package com.example.rss.data.repository.datasource;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.rss.data.database.AppDatabase;
import com.example.rss.data.repository.datasource.impl.DatabaseDataStore;
import com.example.rss.data.cache.ICacheApp;
import com.example.rss.data.repository.datasource.impl.DiskDataStore;
import com.example.rss.data.repository.datasource.impl.NetworkDataStore;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChannelDataStoreFactory {
	private final Context context;
	private final ICacheApp cacheDataStore;


	@Inject
	public ChannelDataStoreFactory(@NonNull Context context, @NonNull ICacheApp cacheDataStore) {
		this.context = context;
		this.cacheDataStore = cacheDataStore;
	}

	public IDataStore createPut() {
		return createDatabaseDataStore();
	}

	@NotNull
	private IDataStore createDatabaseDataStore() {
		AppDatabase appDatabase = AppDatabase.getInstance(context);
		return new DatabaseDataStore(appDatabase, cacheDataStore);
	}

	public IDataStore createNetwork(){
		return new NetworkDataStore();
	}

	public IDataStore createForChannel(Long id) {
		IDataStore dataStore;

		if (id != null){
			if (!cacheDataStore.isExpired() && cacheDataStore.isCachedChannel(id))
				dataStore = new DiskDataStore(cacheDataStore);
			else
				dataStore = createDatabaseDataStore();
		}else
		{
			dataStore = createDatabaseDataStore();
		}

		return dataStore;
	}

	public IDataStore createForCategory(Long id) {
		IDataStore dataStore;

		if (id != null){
			//check cache
			dataStore = createDatabaseDataStore();
		}else
		{
			dataStore = createDatabaseDataStore();
		}

		return dataStore;
	}

	public IDataStore createForItems(Long id) {
		IDataStore dataStore;

		if (id != null){
			//check cache
			dataStore = createDatabaseDataStore();
		}else
		{
			dataStore = createDatabaseDataStore();
		}

		return dataStore;
	}

	public IDataStore createForFile(Long id) {
		IDataStore dataStore;

		if (id != null){
			//check cache
			dataStore = createDatabaseDataStore();
		}else
		{
			dataStore = createDatabaseDataStore();
		}

		return dataStore;
	}
}
