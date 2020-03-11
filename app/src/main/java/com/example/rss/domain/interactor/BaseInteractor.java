package com.example.rss.domain.interactor;

import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;

import io.reactivex.CompletableTransformer;
import io.reactivex.MaybeTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseInteractor {

    private final IThreadExecutor threadExecutor;
    private final IPostExecutionThread postExecutionThread;

    public BaseInteractor(IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    public <T> MaybeTransformer<T, T> getIOTransformerMaybe() {
        return objectObservable -> objectObservable
                .subscribeOn(Schedulers.from(threadExecutor));
    }

    public <T> MaybeTransformer<T, T> getIOToMainTransformerMaybe() {
        return objectObservable -> objectObservable
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }

    public <T> ObservableTransformer<T, T> getIOTransformerObservable() {
        return objectObservable -> objectObservable
                .subscribeOn(Schedulers.from(threadExecutor));
    }

    public <T> ObservableTransformer<T, T> getIOToMainTransformerObservable() {
        return objectObservable -> objectObservable
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }

    public <T> SingleTransformer<T, T> getIOTransformerSingle() {
        return objectObservable -> objectObservable
                .subscribeOn(Schedulers.from(threadExecutor));
    }

    public <T> SingleTransformer<T, T> getIOToMainTransformerSingle() {
        return objectObservable -> objectObservable
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }

    public CompletableTransformer getIOTransformerCompletable() {
        return objectObservable -> objectObservable
                .subscribeOn(Schedulers.from(threadExecutor));
    }

    public CompletableTransformer getIOToMainTransformerCompletable() {
        return objectObservable -> objectObservable
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }
}
