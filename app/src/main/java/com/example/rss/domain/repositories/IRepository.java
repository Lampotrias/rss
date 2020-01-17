package com.example.rss.domain.repositories;

import com.example.rss.domain.Channel;
import com.example.rss.domain.File;
import com.example.rss.domain.Item;

import java.util.List;

import io.reactivex.Single;

public interface IRepository {
	//Network only
	Single<String> getRssFeedContent(String path);

	//Channel
	Single<Long> addChannel(Channel channel);
	Single<Channel> getChannelById(Long id);
	Single<List<Item>> getRowsByChannelId(Long id);

	//File
	Single<Long> addFile(File file);
}
