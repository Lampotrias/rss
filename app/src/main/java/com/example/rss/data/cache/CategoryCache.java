package com.example.rss.data.cache;

import android.content.Context;

import com.example.rss.data.entity.CategoryEntity;
import com.example.rss.domain.executor.IThreadExecutor;

import java.io.IOException;

public class CategoryCache extends Cache<CategoryEntity> {

    private static final String DEFAULT_FILE_NAME = "cat_";
    private static final String CHANNEL_CACHE_FOLDER = "cat";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update_category";

    CategoryCache(Context context, Serializer serializer, FileManager fileManager, IThreadExecutor threadExecutor) throws IOException {
        super(context, serializer, fileManager, threadExecutor);
    }

    @Override
    public long getExpirationTime() {
        return EXPIRATION_TIME;
    }

    @Override
    public String getSettingsKeyLastCacheUpdate() {
        return SETTINGS_KEY_LAST_CACHE_UPDATE;
    }

    @Override
    public String getEntityCacheDir() {
        return CHANNEL_CACHE_FOLDER;
    }

    @Override
    public String getDefaultFileName() {
        return DEFAULT_FILE_NAME;
    }

    @Override
    public Class getEntityClass() {
        return CategoryEntity.class;
    }
}
