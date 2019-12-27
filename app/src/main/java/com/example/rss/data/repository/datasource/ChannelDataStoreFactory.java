package com.example.rss.data.repository.datasource;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.rss.data.database.AppDatabase;
import com.example.rss.data.repository.datasource.impl.DatabaseChannelDataStore;
import com.example.rss.data.repository.datasource.impl.NetworkApi;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Singleton
public class ChannelDataStoreFactory {
	private final Context context;

	@Inject
	public ChannelDataStoreFactory(@NonNull Context context) {
		this.context = context;
	}

	public IChannelDataStore create() {
		IChannelDataStore userDataStore;
		userDataStore = createNetworkApi();
		return userDataStore;
	}

	private IChannelDataStore createDatabaseDataStore() {
		AppDatabase appDatabase = AppDatabase.getInstance(context);
		return new DatabaseChannelDataStore(appDatabase);
	}

	private IChannelDataStore createNetworkApi(){
		final OkHttpClient client;
		final HttpLoggingInterceptor networkLogInterceptor;

		networkLogInterceptor = new HttpLoggingInterceptor();
		networkLogInterceptor.level(HttpLoggingInterceptor.Level.BASIC);

		client = new OkHttpClient.Builder()
				.addInterceptor(networkLogInterceptor)
				.build();

		return new NetworkApi(client);
	}
}
