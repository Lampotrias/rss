package com.example.rss.data.cache;

import android.content.Context;

import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.domain.executor.IThreadExecutor;

import java.io.IOException;

public class ChannelCache extends Cache<ChannelEntity> {

    private static final String DEFAULT_FILE_NAME = "c_";
    private static final String CHANNEL_CACHE_FOLDER = "cn";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update_channel";

    public ChannelCache(Context context, Serializer serializer, FileManager fileManager, IThreadExecutor executor) throws IOException {
        super(context, serializer, fileManager, executor);
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
        return ChannelEntity.class;
    }
}
