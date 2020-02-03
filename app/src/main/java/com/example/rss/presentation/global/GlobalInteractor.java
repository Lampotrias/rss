package com.example.rss.presentation.global;

import androidx.room.Insert;

import com.example.rss.domain.Category;
import com.example.rss.domain.Channel;
import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;
import com.example.rss.domain.repositories.IRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class GlobalInteractor {

    private final IThreadExecutor threadExecutor;
    private final IPostExecutionThread postExecutionThread;
    private final IRepository channelRepository;

    @Inject
    public GlobalInteractor(IRepository channelRepository, IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread){
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.channelRepository = channelRepository;
    }

    Flowable<List<Channel>> getAllChannels(){
        return channelRepository.getAllChannels()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }

    Flowable<List<Category>> getCategoriesByType(String mType){
        return channelRepository.getCategoriesByType(mType)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }
}
