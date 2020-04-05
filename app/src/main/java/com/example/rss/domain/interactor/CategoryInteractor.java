package com.example.rss.domain.interactor;

import com.example.rss.domain.Category;
import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;
import com.example.rss.domain.repositories.IRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;

public class CategoryInteractor extends BaseInteractor {
    private final IRepository repository;

    public final static String CATEGORY_TYPE = "C";
    public final static String FAVORITE_TYPE = "F";

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

    public Maybe<List<Category>> getCategoriesByType(String mType){
        return repository.getCategoriesByType(mType).compose(getIOToMainTransformerMaybe());
    }

    public Maybe<Integer> deleteCategoryById(Long id){
        return repository.deleteCategoryById(id).compose(getIOToMainTransformerMaybe());
    }

    public Maybe<Integer> updateCategoryNameById(Long id, String name){
        return repository.updateCategoryNameById(id, name).compose(getIOToMainTransformerMaybe());
    }
}
