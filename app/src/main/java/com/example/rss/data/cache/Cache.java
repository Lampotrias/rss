package com.example.rss.data.cache;

import io.reactivex.Maybe;

public abstract class Cache<T> {
    public abstract Boolean isExpired();
    public abstract Boolean isCached(Long id);
    public abstract void put (T entity);
    public abstract Maybe<T> get (Long id);
    public abstract void evictAll();
}
