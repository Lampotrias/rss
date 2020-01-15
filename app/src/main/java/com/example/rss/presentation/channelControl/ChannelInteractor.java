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

	public Single<Long> add(String url, Boolean bCacheImage, Boolean bDownloadFull, Boolean bOnlyWifi){
		return
				channelRepository.getRssFeedContent(url)
				.flatMap(this::parseChannel)
				.map(channel -> prepareChannelObj(channel, bCacheImage, bDownloadFull, bOnlyWifi))
				.flatMap(channelRepository::addChannel)
						.doOnError(throwable -> Log.e("myApp", throwable.getMessage()))
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());
	}

	private Channel prepareChannelObj(Channel obj, Boolean bCacheImage, Boolean bDownloadFull, Boolean bOnlyWifi){
		obj.setCacheImage(bCacheImage);
		obj.setDownloadFullText(bDownloadFull);
		obj.setOnlyWifi(bOnlyWifi);
		obj.setTitle("title");
		obj.setCategoryId("setCategoryId");
		obj.setDescription("setDescription");
		obj.setImageId(1);
		obj.setLink("setLink");
		obj.setLastBuild("setLastBuild");
		obj.setNextSyncDate("setNextSyncDate");
		return obj;
	}


	private Single<Channel> parseChannel(String stream){
		return Single.create(emitter -> {
			Channel channel;
			try {
				XmlParser parser = new XmlParser(stream);
				parser.parse();
				channel = new Channel();
				emitter.onSuccess(channel);
			} catch (IOException e) {
				emitter.onError(e);
			}
		});
	}
}
