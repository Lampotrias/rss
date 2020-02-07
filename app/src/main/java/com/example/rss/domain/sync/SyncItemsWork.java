package com.example.rss.domain.sync;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class SyncItemsWork extends Worker {
    private final CompositeDisposable compositeDisposable;
    private final Integer interval;

    @Inject
    WorkManagerInteractor interactor;

    public SyncItemsWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        interval = getInputData().getInt("VAL_SYNC_INTERVAL_TIMESTAMP", 3600);
        compositeDisposable = new CompositeDisposable();
    }

    @NonNull
    @Override
    public Result doWork() {
        compositeDisposable.add(interactor.getChannelForSync(interval).subscribe());
        return Result.success();
    }
}
