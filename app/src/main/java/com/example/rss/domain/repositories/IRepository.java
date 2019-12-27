package com.example.rss.domain.repositories;

import com.example.rss.domain.Channel;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface IRepository {
	Single<String> getRssFeedContent(String path);
}
