package com.example.rss.presentation.channelControl;


import com.example.rss.domain.Channel;
import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;
import com.example.rss.domain.repositories.IRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class CInteractor {

	private final IThreadExecutor threadExecutor;
	private final IPostExecutionThread postExecutionThread;
	private final IRepository channelRepository;

	@Inject
	public CInteractor(IRepository channelRepository, IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread){
		this.threadExecutor = threadExecutor;
		this.postExecutionThread = postExecutionThread;
		this.channelRepository = channelRepository;
	}


	public Single<Long> add(String s){
		return getRssFeedContent(s)
				.map(this::transformToChannel)
				.flatMap(this::addSource)
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());

	}

	private Single<Long> addSource(Channel channel){
		return Single.create(emitter -> emitter.onSuccess(Long.valueOf(123123)));
	}

	private Single<String> getRssFeedContent (String s){
		return channelRepository.getRssFeedContent(s);
	}

	private Channel transformToChannel(String s){
		return new Channel(1);
	}

}
