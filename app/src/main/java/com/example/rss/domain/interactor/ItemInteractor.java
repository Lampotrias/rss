package com.example.rss.domain.interactor;

import com.example.rss.domain.Item;
import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;
import com.example.rss.domain.repositories.IRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;

public class ItemInteractor extends BaseInteractor {
    private final IRepository repository;

    @Inject
    public ItemInteractor(IRepository repository, IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    public Maybe<List<Item>> getItemsByChannelId (Long id) {
        return repository.getItemsByChannelId(id).compose(getIOToMainTransformerMaybe());
    }
}
