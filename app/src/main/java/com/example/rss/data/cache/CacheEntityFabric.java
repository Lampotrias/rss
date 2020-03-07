package com.example.rss.data.cache;

import android.content.Context;

import com.example.rss.domain.executor.IThreadExecutor;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CacheEntityFabric {

    private final FileManager fileManager;
    private final Serializer serializer;
    private final Context context;
    private final IThreadExecutor threadExecutor;

    @Inject
    public CacheEntityFabric(Context context, Serializer serializer, FileManager fileManager, IThreadExecutor threadExecutor) {
        this.context = context;
        this.serializer = serializer;
        this.fileManager = fileManager;
        this.threadExecutor = threadExecutor;
    }

    public Cache getChannelCache() throws IOException {
        return new ChannelCache(context, serializer, fileManager, threadExecutor);
    }

    public Cache getCategoryCache() throws IOException {
        return new CategoryCache(context, serializer, fileManager, threadExecutor);
    }

}
