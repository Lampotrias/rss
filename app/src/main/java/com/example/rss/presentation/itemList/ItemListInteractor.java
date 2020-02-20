package com.example.rss.presentation.itemList;


import android.util.Log;

import com.example.rss.domain.Channel;
import com.example.rss.domain.File;
import com.example.rss.domain.Item;
import com.example.rss.domain.exception.XmlParseException;
import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;
import com.example.rss.domain.repositories.IRepository;
import com.example.rss.domain.xml.XmlChannelRawObject;
import com.example.rss.domain.xml.XmlParser;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

class ItemListInteractor {

	private final IThreadExecutor threadExecutor;
	private final IPostExecutionThread postExecutionThread;
	private final IRepository channelRepository;

	@Inject
	public ItemListInteractor(IRepository channelRepository, IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread){
		this.threadExecutor = threadExecutor;
		this.postExecutionThread = postExecutionThread;
		this.channelRepository = channelRepository;
	}

	Maybe<List<Item>> getAllItems () {
		return channelRepository.getAllItems()
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());
	}

	Maybe<List<Item>> getItemsByChannelId (Long id) {
		return channelRepository.getItemsByChannelId(id)
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());
	}

	Maybe<File> getFileById (Long id) {
		return channelRepository.getFileById(id)
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());
	}

	Maybe<Channel> getChannelById(Long id){
		return channelRepository.getChannelById(id)
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());

	}

	Maybe<List<Long>> InsertItems(List<Item> items){
		return channelRepository.InsertManyItems(items)
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());
	}

	Completable deleteAllItems(){
		return channelRepository.deleteAllItems()
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());
	}

	Completable deleteAllChannels(){
		return channelRepository.deleteAllChannels()
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());
	}

}
