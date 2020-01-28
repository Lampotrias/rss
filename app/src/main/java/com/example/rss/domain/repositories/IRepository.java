package com.example.rss.domain.repositories;

import com.example.rss.domain.Channel;
import com.example.rss.domain.File;
import com.example.rss.domain.Item;

import java.io.InputStream;
import java.util.List;

import io.reactivex.Single;

public interface IRepository {
	//Network only
	Single<InputStream> getRssFeedContent(String path);

	//Channel
	Single<Long> addChannel(Channel channel);
	Single<Channel> getChannelById(Long id);
	Single<List<Item>> getRowsByChannelId(Long id);

	Single<Channel> getChannelByUrl(String url);


	//File
	Single<Long> addFile(File file);
}
