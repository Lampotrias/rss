package com.example.rss.presentation.channelControl;


import android.util.Log;

import com.example.rss.data.xml.XmlParser;
import com.example.rss.domain.Channel;
import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;
import com.example.rss.domain.repositories.IRepository;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ChannelInteractor {

	private final IThreadExecutor threadExecutor;
	private final IPostExecutionThread postExecutionThread;
	private final IRepository channelRepository;

	@Inject
	public ChannelInteractor(IRepository channelRepository, IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread){
		this.threadExecutor = threadExecutor;
		this.postExecutionThread = postExecutionThread;
		this.channelRepository = channelRepository;
	}



	public Single<Long> AddChannelByUrl(String url){
		return getRssFeedContent(url)
				.flatMap(this::parseStringToChannel)
				.flatMap(channelRepository::addChannel)
				.observeOn(Schedulers.from(threadExecutor))
				.subscribeOn(postExecutionThread.getScheduler());
	}

	private Single<String> getRssFeedContent(String url){
		return channelRepository.getRssFeedContent(url);
	}

	private Single<Channel>parseStringToChannel(String sourceString){
		return Single.create(emitter -> {
			//TODO change to java steams
			XmlParser parser = new XmlParser(sourceString);
			try {
				Channel channel = parser.parseChannel();
				emitter.onSuccess(channel);
			} catch (IOException e) {
				emitter.onError(e);
			}
		});
	}
}
