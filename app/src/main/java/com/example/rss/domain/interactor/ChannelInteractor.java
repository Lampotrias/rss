package com.example.rss.domain.interactor;
import android.annotation.SuppressLint;
import android.content.Context;

import androidx.core.util.Preconditions;

import com.example.rss.data.repository.AppDataRepository;
import com.example.rss.domain.Channel;
import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;
import com.example.rss.domain.repositories.IRepository;


import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;

public class ChannelInteractor extends UseCase<Integer, String> {

	private final IRepository channelRepository;

	@Inject
	public ChannelInteractor(IRepository repository, IThreadExecutor ThreadExecutor, IPostExecutionThread PostExecutionThread) {
		super(ThreadExecutor, PostExecutionThread);
		channelRepository = repository;
	}


	@Override
	Observable<Integer> buildUseCaseObservable(String s) {
		return channelRepository.getRssFeedContent(s)
				.map(source-> source.length())
				.toObservable();
	}
}
