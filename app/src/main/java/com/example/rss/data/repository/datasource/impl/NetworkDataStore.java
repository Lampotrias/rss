package com.example.rss.data.repository.datasource.impl;

import com.example.rss.data.entity.CategoryEntity;
import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.FavoriteEntity;
import com.example.rss.data.entity.FileEntity;
import com.example.rss.data.entity.ItemEntity;
import com.example.rss.data.exception.NetworkConnectionException;
import com.example.rss.data.repository.datasource.IDataStore;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class NetworkDataStore implements IDataStore {

    public NetworkDataStore() {
    }

    @Override
    public Maybe<InputStream> getRssFeedContent(String path) {
        return Maybe.create(emitter -> {
            InputStream stream;
            try {
                stream = new URL(path).openStream();
                emitter.onSuccess(stream);
            }catch (Exception e){
                emitter.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Maybe<Long> addChannel(ChannelEntity channel) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<ChannelEntity> getChannelById(Long id) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<List<ItemEntity>> getItemsByChannelId(Long id) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<List<ItemEntity>> getAllItems() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<List<Long>> InsertManyItems(List<ItemEntity> itemEntities) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<ItemEntity> getItemByUniqueId(String hash) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Completable deleteAllItems() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Completable updateReadById(Long id, Boolean isRead) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Single<ChannelEntity> getChannelByUrl(String url) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<List<ChannelEntity>> getAllChannels() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Single<Integer> updateChannel(ChannelEntity channelEntity) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Completable deleteAllChannels() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<Long> addFile(FileEntity fileEntity) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<FileEntity> getFileById(Long id) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<List<CategoryEntity>> getCategoriesByType(String mType) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<CategoryEntity> getCategoryById(Long id) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<Long> addCategory(CategoryEntity categoryEntity) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Maybe<Integer> updateNextExec(Long channelId, Long nextTimestamp) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Completable deleteFavByItemBy(Long id) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Completable insertFavorite(FavoriteEntity favoriteEntity) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Completable deleteAllFavorites() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }
}
