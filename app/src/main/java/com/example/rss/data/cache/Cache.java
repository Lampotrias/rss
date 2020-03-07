package com.example.rss.data.cache;

import android.content.Context;
import android.util.Log;

import com.example.rss.data.entity.Entity;
import com.example.rss.domain.executor.IThreadExecutor;

import java.io.File;
import java.io.IOException;

import io.reactivex.Maybe;

public abstract class Cache<T extends Entity> {
    private static final String SETTINGS_FILE_NAME = "com.example.rss.SETTINGS";

    public abstract long getExpirationTime();

    public abstract String getSettingsKeyLastCacheUpdate();

    public abstract String getEntityCacheDir();

    public abstract String getDefaultFileName();

    public abstract Class getEntityClass();

    private final Context context;
    private final File cacheDir;
    private final Serializer serializer;
    private final FileManager fileManager;
    private final IThreadExecutor threadExecutor;

    Cache(Context context, Serializer serializer, FileManager fileManager, IThreadExecutor threadExecutor) throws IOException {
        if (context == null || serializer == null || fileManager == null || threadExecutor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context.getApplicationContext();
        this.cacheDir = new File(this.context.getCacheDir(), getEntityCacheDir());

        if (!cacheDir.exists()) {
            boolean isOk = cacheDir.mkdirs();
            if (!isOk)
                throw new IOException("cache dir not created");
        }
        this.serializer = serializer;
        this.fileManager = fileManager;
        this.threadExecutor = threadExecutor;
    }

    public void put(T t) {
        if (t != null) {
            String cacheId = t.getId().toString();
            final File entityFile = buildFile(cacheId);
            if (!isCached(cacheId)) {
                final String jsonString = serializer.serialize(t, getEntityClass());
                this.executeAsynchronously(new CacheWriter(this.fileManager, entityFile, jsonString));
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    public Maybe<T> get(String entityId) {
        final File userEntityFile = buildFile(entityId);
        final String fileContent = this.fileManager.readFileContent(userEntityFile);
        T entity = (T) this.serializer.deserialize(fileContent, getEntityClass());
        return Maybe.create(emitter -> {
            if (entity != null) {
                Log.e("logo", "from cache" + entity.getId());
                emitter.onSuccess(entity);
            } else {
                emitter.onError(new Exception());
            }
        });
    }

    ;

    private long getLastCacheUpdateTimeMillis() {
        return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME, getSettingsKeyLastCacheUpdate());
    }

    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }

    public void evictByEntityId(String entityId) {
        this.executeAsynchronously(new CacheRemover(this.fileManager, buildFile(entityId)));
    }

    private void evictAll() {
        this.executeAsynchronously(new CacheRemover(this.fileManager, this.cacheDir));
    }

    private void setLastCacheUpdateTimeMillis() {
        final long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME, getSettingsKeyLastCacheUpdate(), currentMillis);
    }

    public Boolean isCached(String cacheId) {
        final File entityCacheFile = this.buildFile(cacheId);
        return this.fileManager.exists(entityCacheFile);
    }

    private File buildFile(String name) {
        final StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(getDefaultFileName());
        fileNameBuilder.append(name);

        return new File(fileNameBuilder.toString());
    }

    public Boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = getLastCacheUpdateTimeMillis();

        boolean expired = ((currentTime - lastUpdateTime) > getExpirationTime());

        if (expired) {
            this.evictAll();
        }
        return expired;
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

        @Override
        public void run() {
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

        @Override
        public void run() {
            this.fileManager.clear(this.cacheDir);
        }
    }
}
