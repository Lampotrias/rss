package com.example.rss.presentation.global;


import com.example.rss.domain.Category;
import com.example.rss.domain.Channel;
import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;
import com.example.rss.domain.interactor.BaseInteractor;
import com.example.rss.domain.repositories.IRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

class GlobalInteractor extends BaseInteractor {

    private final IThreadExecutor threadExecutor;
    private final IPostExecutionThread postExecutionThread;
    private final IRepository channelRepository;

    @Inject
    public GlobalInteractor(IRepository channelRepository, IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread){
        super(threadExecutor, postExecutionThread);
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.channelRepository = channelRepository;
    }

    Maybe<List<Channel>> getAllChannels(){
        return channelRepository.getAllChannels()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }

    Maybe<List<Category>> getCategoriesByType(String mType){
        return channelRepository.getCategoriesByType(mType)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }
}
