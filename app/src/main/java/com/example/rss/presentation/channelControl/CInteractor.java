package com.example.rss.presentation.channelControl;


import com.example.rss.domain.Channel;
import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;
import com.example.rss.domain.repositories.IRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
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


	public Observable<String> add(String s){
		return getRssFeedContent(s)
				.map(this::transformToChannel)
				.flatMap(this::addSource)
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());

	}

	Observable<String> addSource(Channel channel){
		return Observable.create(emitter -> {emitter.onNext("76"); emitter.onComplete();});
	}

	Observable<String> getRssFeedContent (String s){
		return channelRepository.getRssFeedContent(s).toObservable();
	}

	Channel transformToChannel(String s){
		return new Channel(1);
	}

}
