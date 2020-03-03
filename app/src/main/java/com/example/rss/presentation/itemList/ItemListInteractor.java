package com.example.rss.presentation.itemList;

import android.util.Log;

import com.example.rss.domain.Channel;
import com.example.rss.domain.Favorite;
import com.example.rss.domain.File;
import com.example.rss.domain.Item;
import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;
import com.example.rss.domain.repositories.IRepository;
import com.example.rss.domain.xml.XmlItemRawObject;
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
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

class ItemListInteractor {

	private final IThreadExecutor threadExecutor;
	private final IPostExecutionThread postExecutionThread;
	private final IRepository repository;

	@Inject
	public ItemListInteractor(IRepository repository, IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread){
		this.threadExecutor = threadExecutor;
		this.postExecutionThread = postExecutionThread;
		this.repository = repository;
	}
	Maybe<List<Item>> getAllItems () {
		return repository.getAllItems()
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());
	}
	Maybe<List<Item>> getItemsByChannelId (Long id) {
		return repository.getItemsByChannelId(id)
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());
	}
	Maybe<File> getFileById (Long id) {
		return repository.getFileById(id)
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());
	}
	Maybe<Channel> getChannelById(Long id){
		return repository.getChannelById(id)
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());

	}
	Completable updateReadById(Long id, Boolean isRead){
		return repository.updateReadById(id, isRead)
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());
	}
	Completable deleteFavByItemBy(Long itemId){
		return repository.deleteFavByItemBy(itemId)
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());
	}
	Completable insertFavorite(Favorite favorite){
		return repository.insertFavorite(favorite)
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());
	}

	Observable<List<Long>> syncGetItems(Long channelId){
        return switchChannel(channelId)
                .flatMapIterable(channels -> channels)
                .flatMapMaybe(channel -> repository.getRssFeedContent(channel.getSourceLink()))
                .map(this::parseItem)
                .concatMapIterable(xmlItemRawObjects -> xmlItemRawObjects)
				.doOnNext(xmlItemRawObject -> {
					Log.e("myApp", "+ " + xmlItemRawObject.getTitle());
				})
				.concatMap(xmlItemRawObject -> repository.getItemByUniqueId(xmlItemRawObject.getGuid())
						.map(item -> {
							xmlItemRawObject.setGuid("");
							Log.e("myApp", "exists " + xmlItemRawObject.getTitle());
							return xmlItemRawObject;
						})
						.toObservable().defaultIfEmpty(xmlItemRawObject))
				.doOnNext(xmlItemRawObject -> Log.e("myApp", "- " + xmlItemRawObject.getTitle()))
                .filter(xmlItemRawObject -> !xmlItemRawObject.getGuid().isEmpty())
                .concatMap(xmlItemRawObject -> processItemXml(xmlItemRawObject, channelId).toObservable())
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());
    }

	Maybe<List<Long>> processItemXml(XmlItemRawObject xmlItemRawObject, Long channelId){
		return saveFile(xmlItemRawObject)
				.map(fileId -> prepareItem(xmlItemRawObject, fileId, channelId))
				.toList()
				.toMaybe()
				.flatMap(repository::InsertManyItems);
	}

	private Observable<Long> saveFile(XmlItemRawObject xmlItemRawObject){
		if (xmlItemRawObject.getEnclosure() != null){
			return repository.addFile(prepareFile(xmlItemRawObject)).toObservable().switchIfEmpty(Observable.just(0L));
		}else
			return Observable.just(0L);
	}

	private File prepareFile(XmlItemRawObject xmlItemRawObject){
		File file = new File();
		file.setDescription(xmlItemRawObject.getEnclosure().getDescription());
		file.setPath(xmlItemRawObject.getEnclosure().getPath());
		file.setExternal(false);
		file.setType("image");
		return file;
	}

	private Item prepareItem(XmlItemRawObject xmlItemRawObject, Long fileId, Long channelId) {
		SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
		Date pubDate = new Date();

		Item item = new Item();
		item.setChannelId(channelId);
		item.setTitle(xmlItemRawObject.getTitle());
		item.setDescription(xmlItemRawObject.getDescription());
		item.setGuid(xmlItemRawObject.getGuid());
		item.setLink(xmlItemRawObject.getLink());
		try {
			pubDate = format.parse(xmlItemRawObject.getPubDate());
		} catch (ParseException ignored) {

		}
		item.setPubDate(pubDate.getTime());
		item.setEnclosure(fileId);
		return item;
	}

    private List<XmlItemRawObject> parseItem (InputStream stream) throws IOException {
        XmlParser xmlParser = new XmlParser(stream);
        return xmlParser.parseItems();
    }

    Observable<List<Channel>> switchChannel(Long channelId){
	    if (channelId > 0){
	        return repository.getChannelById(channelId).toObservable().toList().toObservable();
        }else{
	        return repository.getAllChannels().toObservable();
        }
    }




}
