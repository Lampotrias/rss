package com.example.rss.domain.interactor;

import com.example.rss.domain.Category;
import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;
import com.example.rss.domain.repositories.IRepository;

import javax.inject.Inject;

import io.reactivex.Maybe;

public class CategoryInteractor extends BaseInteractor {
    private final IRepository repository;

    @Inject
    public CategoryInteractor(IRepository repository, IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    public Maybe<Category> getCategoryById(Long id){
        return repository.getCategoryById(id).compose(getIOToMainTransformerMaybe());
    }
    public Maybe<Long> addCategory(Category category){
        return repository.addCategory(category).compose(getIOToMainTransformerMaybe());
    }
}
