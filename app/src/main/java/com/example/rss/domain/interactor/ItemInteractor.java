package com.example.rss.domain.interactor;

import com.example.rss.domain.Favorite;
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

public class ItemInteractor extends BaseInteractor {
    private final IRepository repository;

    @Inject
    public ItemInteractor(IRepository repository, IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    public Maybe<List<Item>> getItemsByChannelId(Long id) {
        return repository.getItemsByChannelId(id).compose(getIOToMainTransformerMaybe());
    }

    public Maybe<Integer> setReadForChannel(Long id) {
        return repository.setReadForChannel(id).compose(getIOToMainTransformerMaybe());
    }

    public Maybe<Integer> setAllRead() {
        return repository.setAllRead().compose(getIOToMainTransformerMaybe());
    }

    public Maybe<Integer> deleteFavByItemBy(Long itemId) {
        return repository.deleteFavByItemBy(itemId).compose(getIOToMainTransformerMaybe());

    }

    public Maybe<Integer> deleteItemById(Long id) {
        //Log.e("Logo", "id   "  + id);
        return repository.deleteItemById(id).compose(getIOToMainTransformerMaybe());

    }

    public Maybe<Long> insertFavorite(Favorite favorite) {
        return repository.insertFavorite(favorite).compose(getIOToMainTransformerMaybe());
    }

    public Maybe<List<Item>> getItemsWithOffsetByChannel(Long channelId, Integer offset, Integer limit) {
        return repository.getItemsWithOffsetByChannel(channelId, offset, limit).compose(getIOToMainTransformerMaybe());
    }

    public Maybe<List<Item>> getFavoritesWithOffset(Integer offset, Integer limit) {
        return repository.getFavoritesWithOffset(offset, limit).compose(getIOToMainTransformerMaybe());
    }

    public Maybe<List<Item>> getAllItems() {
        return repository.getAllItems().compose(getIOToMainTransformerMaybe());
    }

    public Maybe<Integer> updateItemReadById(Long id, Boolean isRead) {
        return repository.updateReadById(id, isRead).compose(getIOToMainTransformerMaybe());
    }

    public Maybe<Item> getItemByUniqueId(String uid) {
        return repository.getItemByUniqueId(uid).compose(getIOToMainTransformerMaybe());
    }

    public Maybe<Integer> getCountItemsByChannel(Long id) {
        return repository.getCountItemsByChannel(id).compose(getIOToMainTransformerMaybe());
    }

    public Maybe<Integer> getPosItemInChannelQueue(Long channelId, Long itemId) {
        return repository.getPosItemInChannelQueue(channelId, itemId).compose(getIOToMainTransformerMaybe());
    }

    public Maybe<List<Long>> insertManyItems(List<Item> items) {
        return repository.insertManyItems(items).compose(getIOToMainTransformerMaybe());
    }

    public Maybe<Integer> deleteAllItems() {
        return repository.deleteAllItems().compose(getIOToMainTransformerMaybe());
    }

    public Maybe<Integer> deleteItemsByChannelId(Long id) {
        return repository.deleteItemsByChannelId(id).compose(getIOToMainTransformerMaybe());
    }

    public Maybe<Integer> deleteAllFavorites() {
        return repository.deleteAllFavorites().compose(getIOToMainTransformerMaybe());
    }

    public Maybe<List<XmlItemRawObject>> parseItemsByStream(InputStream stream) {
        Maybe<List<XmlItemRawObject>> obj = Maybe.create(emitter -> {
            try {
                XmlParser xmlParser = new XmlParser(stream);
                emitter.onSuccess(xmlParser.parseItems());
                //emitter.onComplete();
            } catch (IOException e) {
                emitter.onError(e);
            }
        });
        return obj.compose(getIOToMainTransformerMaybe());
    }

    public Item prepareItem(XmlItemRawObject xmlItemRawObject, Long fileId, Long channelId) {
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
}
