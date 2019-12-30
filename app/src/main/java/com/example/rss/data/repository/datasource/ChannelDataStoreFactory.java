package com.example.rss.data.repository.datasource;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.rss.data.database.AppDatabase;
import com.example.rss.data.repository.datasource.impl.DatabaseDataStore;
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

	public IDataStore create() {
		IDataStore userDataStore;
		userDataStore = createNetworkApi();
		return userDataStore;
	}

	private IDataStore createDatabaseDataStore() {
		AppDatabase appDatabase = AppDatabase.getInstance(context);
		return new DatabaseDataStore(appDatabase);
	}

	private IDataStore createNetworkApi(){
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
