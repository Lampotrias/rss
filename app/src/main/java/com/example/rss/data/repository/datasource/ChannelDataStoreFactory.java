package com.example.rss.data.repository.datasource;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.rss.data.cache.Cache;
import com.example.rss.data.cache.CacheEntityFabric;
import com.example.rss.data.database.AppDatabase;
import com.example.rss.data.repository.datasource.impl.DatabaseDataStore;
import com.example.rss.data.repository.datasource.impl.DiskDataStore;
import com.example.rss.data.repository.datasource.impl.NetworkDataStore;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChannelDataStoreFactory {
    private final Context context;
    private final CacheEntityFabric cacheEntityFabric;


    @Inject
    public ChannelDataStoreFactory(@NonNull Context context, @NonNull CacheEntityFabric cacheEntityFabric) {
        this.context = context;
        this.cacheEntityFabric = cacheEntityFabric;
    }

    @NotNull
    private IDataStore createDatabaseDataStore() {
        return createDatabaseDataStore(null);
    }

    @NotNull
    private IDataStore createDatabaseDataStore(Cache cache) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        return new DatabaseDataStore(appDatabase, cache);
    }

    public IDataStore createNetwork() {
        return new NetworkDataStore();
    }

    public IDataStore createForFavorite(Long id) {
        IDataStore dataStore;

        if (id != null) {
            //check cache
            dataStore = createDatabaseDataStore();
        } else {
            dataStore = createDatabaseDataStore();
        }

        return dataStore;
    }

    public IDataStore createForChannel(Long id) {
        Cache cacheObj;
        try {
            cacheObj = cacheEntityFabric.getChannelCache();
        } catch (IOException e) {
            return createDatabaseDataStore();
        }

        if (id != null) {
            if (!cacheObj.isExpired() && cacheObj.isCached(id.toString()))
                return new DiskDataStore(cacheObj);
        }
        return createDatabaseDataStore(cacheObj);
    }

    public IDataStore createForCategory(Long id) {
        Cache cacheObj;
        try {
            cacheObj = cacheEntityFabric.getCategoryCache();
        } catch (IOException e) {
            return createDatabaseDataStore();
        }

        if (id != null) {
            if (!cacheObj.isExpired() && cacheObj.isCached(id.toString()))
                return new DiskDataStore(cacheObj);
        }
        return createDatabaseDataStore(cacheObj);
    }

    public IDataStore createForItems(Long id) {
        IDataStore dataStore;

        if (id != null) {
            //check cache
            dataStore = createDatabaseDataStore();
        } else {
            dataStore = createDatabaseDataStore();
        }

        return dataStore;
    }

    public IDataStore createForFile(Long id) {
        IDataStore dataStore;

        if (id != null) {
            //check cache
            dataStore = createDatabaseDataStore();
        } else {
            dataStore = createDatabaseDataStore();
        }

        return dataStore;
    }
}
