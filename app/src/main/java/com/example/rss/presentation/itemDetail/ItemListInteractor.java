package com.example.rss.presentation.itemDetail;


import com.example.rss.domain.File;
import com.example.rss.domain.Item;
import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;
import com.example.rss.domain.repositories.IRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

class ItemDetailInteractor {

	private final IThreadExecutor threadExecutor;
	private final IPostExecutionThread postExecutionThread;
	private final IRepository channelRepository;

	@Inject
	public ItemDetailInteractor(IRepository channelRepository, IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread){
		this.threadExecutor = threadExecutor;
		this.postExecutionThread = postExecutionThread;
		this.channelRepository = channelRepository;
	}

}
