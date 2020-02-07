package com.example.rss.presentation.channelEdit;


import com.example.rss.domain.exception.XmlParseException;
import com.example.rss.domain.xml.XmlChannelRawObject;
import com.example.rss.domain.xml.XmlParser;
import com.example.rss.domain.Channel;
import com.example.rss.domain.File;
import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;
import com.example.rss.domain.repositories.IRepository;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

class ChannelEditInteractor {

	private final IThreadExecutor threadExecutor;
	private final IPostExecutionThread postExecutionThread;
	private final IRepository channelRepository;

	@Inject
	public ChannelEditInteractor(IRepository channelRepository, IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread){
		this.threadExecutor = threadExecutor;
		this.postExecutionThread = postExecutionThread;
		this.channelRepository = channelRepository;
	}

	Single<Channel> checkChannelExistsByUrl(String url){
		return channelRepository.getChannelByUrl(url)
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());
	}

	Single<Channel> getChannelById(Long id){
		return channelRepository.getChannelById(id)
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());
	}

	Maybe<Long> addChannel(String url, Long categoryId, Boolean bCacheImage, Boolean bDownloadFull, Boolean bOnlyWifi){

		return	channelRepository.getRssFeedContent(url)
				.map(this::parseChannel)
				.flatMap(xmlChannelRawObject -> saveFile(prepareFile(xmlChannelRawObject)).toMaybe()
						.map(fileId -> prepareChannelObj(xmlChannelRawObject, url, fileId, categoryId, bCacheImage, bDownloadFull, bOnlyWifi)))
				.flatMap(channelRepository::addChannel)
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());
	}


	private Channel prepareChannelObj(XmlChannelRawObject xmlChannelRawObject, String url, Long fileId, Long categoryId, Boolean bCacheImage, Boolean bDownloadFull, Boolean bOnlyWifi) {
		Channel channel = new Channel();
		channel.setTitle(xmlChannelRawObject.getTitle());
		channel.setLink(xmlChannelRawObject.getLink());
		channel.setDescription(xmlChannelRawObject.getDescription());
		channel.setLink(xmlChannelRawObject.getLink());
		channel.setSourceLink(url);
		channel.setFileId(fileId);
		channel.setCategoryId(categoryId);
		channel.setCacheImage(bCacheImage);
		channel.setDownloadFullText(bDownloadFull);
		channel.setOnlyWifi(bOnlyWifi);

		try {
			SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
			Date dateLastSync, dateNextExec = new Date();
			dateLastSync = format.parse(xmlChannelRawObject.getLastBuild());
			if (dateLastSync != null) {
				channel.setLastBuild(dateLastSync.getTime());
			}
			channel.setNextSyncDate(dateNextExec.getTime() + 3600);
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

	private XmlChannelRawObject parseChannel(InputStream stream) throws XmlParseException {
			Channel channel;
			try {
				XmlParser parser = new XmlParser(stream);
				return parser.parseChannel();
			} catch (IOException e) {
				throw new XmlParseException(e.getCause());
			}
	}
}
