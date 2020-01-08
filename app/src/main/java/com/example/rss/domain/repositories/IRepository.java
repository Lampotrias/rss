package com.example.rss.domain.repositories;

import com.example.rss.domain.Channel;
import com.example.rss.domain.Item;

import java.util.List;

import io.reactivex.Single;

public interface IRepository {
	Single<String> getRssFeedContent(String path);
	Single<Long> addChannel(Channel channel);
	Single<Channel> getChannelById(Integer id);
	Single<List<Item>> getRowsByChannelId(Integer id);
}
