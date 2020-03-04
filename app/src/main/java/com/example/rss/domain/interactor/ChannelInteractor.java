package com.example.rss.domain.interactor;

import com.example.rss.domain.Channel;
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

import io.reactivex.Maybe;
import io.reactivex.Single;

public class ChannelInteractor extends BaseInteractor {

    private final IRepository repository;

    @Inject
    public ChannelInteractor(IRepository repository, IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    public Maybe<List<Channel>> getAllChannels(){
        return repository.getAllChannels().compose(getIOToMainTransformerMaybe());
    }

    public Maybe<Channel> getChannelById(Long id){
        return repository.getChannelById(id).compose(getIOToMainTransformerMaybe());
    }

    public Single<Channel> checkChannelExistsByUrl(String url){
        return repository.getChannelByUrl(url).compose(getIOToMainTransformerSingle());
    }

    public Maybe<XmlChannelRawObject> getRawChannel(String url){
            return repository.getRssFeedContent(url)
                    .map(this::parseChannel)
                    .compose(getIOToMainTransformerMaybe());
    }

    public Maybe<Long> add(Channel channel){
        return repository.addChannel(channel).compose(getIOToMainTransformerMaybe());
    }

    private XmlChannelRawObject parseChannel(InputStream stream) throws XmlParseException {
        try {
            XmlParser parser = new XmlParser(stream);
            return parser.parseChannel();
        } catch (IOException e) {
            throw new XmlParseException(e.getCause());
        }
    }

    public Channel prepareChannelObj(XmlChannelRawObject xmlChannelRawObject, String url, Long fileId, Long categoryId, Boolean bCacheImage, Boolean bDownloadFull, Boolean bOnlyWifi) {
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
        channel.setNextSyncDate(0L);

        if (xmlChannelRawObject.getLastBuild() != null) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
                Date dateLastSync;
                dateLastSync = format.parse(xmlChannelRawObject.getLastBuild());
                if (dateLastSync != null) {
                    channel.setLastBuild(dateLastSync.getTime() / 1000);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return channel;
    }
}
