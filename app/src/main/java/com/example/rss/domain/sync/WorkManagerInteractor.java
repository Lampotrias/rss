package com.example.rss.domain.sync;

import android.util.Log;

import com.example.rss.domain.Channel;
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

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class WorkManagerInteractor {
    private final IThreadExecutor threadExecutor;
    private final IPostExecutionThread postExecutionThread;
    private final IRepository channelRepository;

    @Inject
    public WorkManagerInteractor(IRepository channelRepository, IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread){
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.channelRepository = channelRepository;
    }

    public Observable<Channel> getChannelForSync(int intervalSec) {
        Long currentTs = new Date().getTime() / 1000;

        return channelRepository.getAllChannels()

                .toObservable()
                .flatMapIterable(channels -> channels)
                .filter(channel -> {
                    Long next = channel.getNextSyncDate();
                    Long last = channel.getLastBuild();
                    if (next == 0)
                        return true;

                    if (next < currentTs) { //если запланированная дата уже наступила, то безусловно обновляемся
                        if (last != null && last != 0) {
                            if (last < next) {
                                return false;
                            }else
                                return next < last && last < currentTs;
                        }else
                            return true; //нет LastBuild
                    }else
                        return false;
                })
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }

    public Observable<XmlItemRawObject> syncItemsByChannel(Channel channel){
        return channelRepository.getRssFeedContent(channel.getSourceLink())
                .map(this::parseItem)
                .toObservable()
                .flatMapIterable(xmlItemRawObjects -> xmlItemRawObjects)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }

    public Maybe<List<Long>> processItemXml(XmlItemRawObject xmlItemRawObject, Long channelId){
        return saveFile(xmlItemRawObject)
                .map(fileId -> prepareItem(xmlItemRawObject, fileId, channelId))
                .toList()
                .toMaybe()
                .flatMap(items -> channelRepository.InsertManyItems(items))
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }

    public Single<Item> checkModify(String hash) {
        return channelRepository.getItemByUniqueId(hash)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }

    private List<XmlItemRawObject> parseItem (InputStream stream) throws IOException {
        XmlParser xmlParser = new XmlParser(stream);
        return xmlParser.parseItems();
    }

    private Observable<Long> saveFile(XmlItemRawObject xmlItemRawObject){
        if (xmlItemRawObject.getEnclosure() != null){
            return channelRepository.addFile(prepareFile(xmlItemRawObject)).toObservable().switchIfEmpty(Observable.just(0L));
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
        Log.e("myApp", "add item " + xmlItemRawObject.getTitle() + " ---- " + fileId);

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
        item.setPubDate(pubDate.getTime() / 1000);
        item.setEnclosure(fileId);
        return item;
    }

    public Single<Integer> updateChannel(Channel channel){
        return channelRepository.updateChannel(channel)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }

}
