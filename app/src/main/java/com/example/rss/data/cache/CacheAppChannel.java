package com.example.rss.data.cache;

import android.content.Context;

import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.domain.executor.IThreadExecutor;
import com.example.rss.presentation.exception.ChannelNotFoundException;

import java.io.File;

import io.reactivex.Maybe;

public class CacheAppChannel extends Cache<ChannelEntity> {

    private static final String DEFAULT_FILE_NAME = "c_";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";
    private static final String SETTINGS_FILE_NAME = "com.example.rss.SETTINGS";

    private final Context context;
    private final File cacheDir;
    private final Serializer serializer;
    private final FileManager fileManager;
    private final IThreadExecutor threadExecutor;

    public CacheAppChannel(Context context, Serializer serializer,
                           FileManager fileManager, IThreadExecutor executor) {
        if (context == null || serializer == null || fileManager == null || executor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context.getApplicationContext();
        this.cacheDir = this.context.getCacheDir();
        this.serializer = serializer;
        this.fileManager = fileManager;
        this.threadExecutor = executor;
    }

    @Override
    public Boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

        boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

        if (expired) {
            this.evictAll();
        }

        return expired;
    }

    @Override public void evictAll() {
        this.executeAsynchronously(new CacheRemover(this.fileManager, this.cacheDir));
    }

    private long getLastCacheUpdateTimeMillis() {
        return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE);
    }

    @Override
    public Boolean isCached(Long id) {
        final File userEntityFile = this.buildFile(id.toString());
        return this.fileManager.exists(userEntityFile);
    }

    @Override
    public void put(ChannelEntity entity) {
        if (entity != null){
            final File channelEntityFile = buildFile(entity.getId().toString());
            if (!isCached(entity.getId())) {
                final String jsonString = serializer.serialize(entity, ChannelEntity.class);
                this.executeAsynchronously(new CacheWriter(this.fileManager, channelEntityFile, jsonString));
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    private void setLastCacheUpdateTimeMillis() {
        final long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
    }

    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }

    private File buildFile (String name){
        final StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(DEFAULT_FILE_NAME);
        fileNameBuilder.append(name);

        return new File(fileNameBuilder.toString());
    }

    @Override
    public Maybe<ChannelEntity> get(Long id) {
        return Maybe.create(emitter -> {
            final File userEntityFile = CacheAppChannel.this.buildFile(id.toString());
            final String fileContent = CacheAppChannel.this.fileManager.readFileContent(userEntityFile);
            final ChannelEntity channelEntity = CacheAppChannel.this.serializer.deserialize(fileContent, ChannelEntity.class);

            if (channelEntity != null) {
                emitter.onSuccess(channelEntity);
            } else {
                emitter.onError(new ChannelNotFoundException());
            }
        });
    }

    private static class CacheWriter implements Runnable {
        private final FileManager fileManager;
        private final File fileToWrite;
        private final String fileContent;

        CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
            this.fileManager = fileManager;
            this.fileToWrite = fileToWrite;
            this.fileContent = fileContent;
        }

        @Override public void run() {
            this.fileManager.writeToFile(fileToWrite, fileContent);
        }
    }

    private static class CacheRemover implements Runnable {
        private final FileManager fileManager;
        private final File cacheDir;

        CacheRemover(FileManager fileManager, File cacheDir) {
            this.fileManager = fileManager;
            this.cacheDir = cacheDir;
        }

        @Override public void run() {
            this.fileManager.clearDirectory(this.cacheDir);
        }
    }
}
