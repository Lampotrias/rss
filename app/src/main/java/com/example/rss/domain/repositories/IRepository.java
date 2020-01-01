package com.example.rss.domain.repositories;

import com.example.rss.domain.Channel;
import com.example.rss.domain.Row;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface IRepository {
	Single<String> getRssFeedContent(String path);
	Single<Long> addChannel(Channel channel);
	Single<Channel> getChannelById(Integer id);
	Single<List<Row>> getRowsByChannelId(Integer id);
}
