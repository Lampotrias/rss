package com.example.rss.domain.sync;

import com.example.rss.domain.Channel;
import com.example.rss.domain.File;
import com.example.rss.domain.Item;
import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;
import com.example.rss.domain.repositories.IRepository;
import com.example.rss.domain.xml.XmlFileRawObject;
import com.example.rss.domain.xml.XmlItemRawObject;
import com.example.rss.domain.xml.XmlParser;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
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

    public Observable<Channel> getChannelForSync(Integer intervalTimestamp) {
        Long currentTs = new Date().getTime();

        return channelRepository.getAllChannels()
                .toObservable()
                .flatMapIterable(channels -> channels)
                //.filter(channel -> currentTs < channel.getNextSyncDate())
                //.toList()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());

//                .flatMap(channel1 -> channelRepository.getRssFeedContent(channel1.getSourceLink()).toObservable())
//                .flatMap(this::parse)
//                .toList()

    }

    public Observable<List<Long>> getItemsByChannel (Channel channel){
        return channelRepository.getRssFeedContent(channel.getSourceLink())
                .toObservable()
                .map(this::parseItem)
                .flatMapIterable(xmlItemRawObjects -> xmlItemRawObjects)

//        Observable.zip(channelRepository.getItemByUniqueId(xmlItemRawObject.getGuid())))
                .flatMap((Function<XmlItemRawObject, ObservableSource<Item>>) xmlItemRawObject -> Observable.zip(saveFile(xmlItemRawObject), prepareItem(xmlItemRawObject), (fileId, item) -> {
                    if (fileId > 0)
                        item.setEnclosure(fileId);
                    return item;
                }))
                .toList().toObservable()
                .flatMap(items -> channelRepository.InsertManyItems(items).toObservable())
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }

    private Observable<Long> saveFile(XmlItemRawObject xmlItemRawObject){
        if (xmlItemRawObject.getEnclosure() != null){
            return channelRepository.addFile(prepareFile(xmlItemRawObject)).toObservable();
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

    private List<XmlItemRawObject> parseItem (InputStream stream) throws IOException {
        XmlParser xmlParser = new XmlParser(stream);
        return xmlParser.parseItems();
    }


    private Observable<Item> prepareItem(XmlItemRawObject xmlItemRawObject) {
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
        Date pubDate = new Date();

        Item item = new Item();
        item.setTitle(xmlItemRawObject.getTitle());
        item.setDescription(xmlItemRawObject.getDescription());
        item.setGuid(xmlItemRawObject.getGuid());
        item.setLink(xmlItemRawObject.getLink());
        try {
            pubDate = format.parse(xmlItemRawObject.getPubDate());
        } catch (ParseException ignored) {

        }
        item.setPubDate(pubDate.getTime());

        return Observable.just(item);

    }


}
