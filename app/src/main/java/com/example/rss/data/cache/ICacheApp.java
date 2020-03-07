package com.example.rss.data.cache;


import io.reactivex.Maybe;

public interface ICacheApp<T> {
    Boolean isExpired();
    Boolean isCached(Long id);
    Maybe<T> getByEntityId(Long id);
    void putEntity(T t);
}
