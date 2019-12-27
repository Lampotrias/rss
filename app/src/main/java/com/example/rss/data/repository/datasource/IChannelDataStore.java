package com.example.rss.data.repository.datasource;

import com.example.rss.domain.Channel;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface IChannelDataStore {
	Single<String> getRssFeedContent(String path);
}
