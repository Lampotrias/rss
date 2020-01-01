package com.example.rss.data.repository.datasource;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.rss.data.database.AppDatabase;
import com.example.rss.data.repository.datasource.impl.DatabaseDataStore;
import com.example.rss.data.repository.datasource.impl.DiskDataStore;
import com.example.rss.data.repository.datasource.impl.ICacheApp;
import com.example.rss.data.repository.datasource.impl.NetworkDataStore;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

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
		return new DatabaseDataStore(appDatabase);
	}

	public IDataStore createNetwork(){
		final OkHttpClient client;
		final HttpLoggingInterceptor networkLogInterceptor;

		networkLogInterceptor = new HttpLoggingInterceptor();
		networkLogInterceptor.level(HttpLoggingInterceptor.Level.BASIC);

		client = new OkHttpClient.Builder()
				.addInterceptor(networkLogInterceptor)
				.build();

		return new NetworkDataStore(client);
	}

	public IDataStore createForChannel(Integer id) {
		IDataStore dataStore;

		if (!cacheDataStore.isExpired() && cacheDataStore.isCachedChannel(id))
			dataStore = new DiskDataStore(cacheDataStore);
		else
			dataStore = createDatabaseDataStore();
		return dataStore;

	}
}
