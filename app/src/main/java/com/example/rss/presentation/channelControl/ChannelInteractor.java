package com.example.rss.presentation.channelControl;


import android.annotation.SuppressLint;
import android.util.Log;

import com.example.rss.data.xml.XmlChannelRawObject;
import com.example.rss.data.xml.XmlFileRawObject;
import com.example.rss.data.xml.XmlParser;
import com.example.rss.domain.Channel;
import com.example.rss.domain.File;
import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;
import com.example.rss.domain.repositories.IRepository;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
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

	public Single<Long> addChannel(String url, Long categoryId, Boolean bCacheImage, Boolean bDownloadFull, Boolean bOnlyWifi){

		return
				channelRepository.getRssFeedContent(url)
				.map(this::parseChannel)
						.flatMap(xmlChannelRawObject -> {
							return saveFile(prepareFile(xmlChannelRawObject))
									.map(fileId -> prepareChannelObj(xmlChannelRawObject, fileId, categoryId, bCacheImage,bDownloadFull,bOnlyWifi));
						})
				.flatMap(channelRepository::addChannel)
						.doOnError(throwable -> Log.e("myApp", Objects.requireNonNull(throwable.getMessage())))
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());
	}

	private Channel prepareChannelObj(XmlChannelRawObject xmlChannelRawObject, Long fileId, Long categoryId, Boolean bCacheImage, Boolean bDownloadFull, Boolean bOnlyWifi) {
		Channel channel = new Channel();
		channel.setTitle(xmlChannelRawObject.getTitle());
		channel.setLink(xmlChannelRawObject.getLink());
		channel.setDescription(xmlChannelRawObject.getDescription());
		channel.setLink(xmlChannelRawObject.getLink());
		channel.setFileId(fileId);
		channel.setCategoryId(categoryId);
		channel.setCacheImage(bCacheImage);
		channel.setDownloadFullText(bDownloadFull);
		channel.setOnlyWifi(bOnlyWifi);

		try {
			SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
			Date dateLastSync, dateNextExec = new Date();
			dateLastSync = format.parse(xmlChannelRawObject.getLastBuild());
			if (dateLastSync != null) {
				channel.setLastBuild(String.valueOf(dateLastSync.getTime()));
			}
			channel.setNextSyncDate(String.valueOf(dateNextExec.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return channel;
	}


	private File prepareFile(XmlChannelRawObject xmlChannelRawObject){
		File file = new File();
		file.setDescription(xmlChannelRawObject.getFile().getDescription());
		file.setPath(xmlChannelRawObject.getFile().getPath());
		file.setExternal(false);
		file.setType("image");
		return file;
}

	private Single<Long> saveFile(File file){
		return channelRepository.addFile(file);
	}

	private XmlChannelRawObject parseChannel(String stream) throws IOException {
			Channel channel;
			try {
				XmlParser parser = new XmlParser(stream);
				return parser.parseChannel();
			} catch (IOException e) {
				throw new IOException(e);
			}
	}
}
