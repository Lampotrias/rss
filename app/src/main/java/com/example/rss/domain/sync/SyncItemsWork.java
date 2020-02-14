package com.example.rss.domain.sync;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.EmptyResultSetException;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class SyncItemsWork extends Worker {
    private final CompositeDisposable disposable;
    private final Integer interval;

    @Inject
    WorkManagerInteractor interactor;

    public SyncItemsWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        interval = getInputData().getInt("VAL_SYNC_INTERVAL_TIMESTAMP", 3600);
        disposable = new CompositeDisposable();
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("myApp", "start sync");
        disposable.add(
                interactor.getChannelForSync(3600)
                        .subscribe(channel -> {
                            Log.e("myApp", "current sync channel = " + channel.getChannelId());
                            disposable.add(interactor.syncItemsByChannel(channel)
                                    .subscribe(
                                            xmlItemRawObject -> {
                                                Log.e("myApp", "get item = " + xmlItemRawObject.getTitle());
                                                disposable.add(interactor.checkModify(xmlItemRawObject.getGuid()).subscribe(
                                                        item -> {
                                                            Log.e("myApp", "item exists = " + item.getTitle());
                                                        },
                                                        throwable -> {
                                                            if (throwable instanceof EmptyResultSetException){
                                                                Log.e("myApp", "item not exists = " + xmlItemRawObject.getTitle());
                                                                //item not exists
                                                                disposable.add(interactor.processItemXml(xmlItemRawObject, channel.getChannelId()).subscribe(
                                                                        longs -> {
                                                                            Log.e("myApp", "add Id = " + longs.get(0));
                                                                        })
                                                                );
                                                            }
                                                        })
                                                );
                                            },
                                            throwable -> {},
                                            () -> {
                                                //TODO make full update channel
                                                Long currentTs = new Date().getTime() / 1000;
                                                channel.setNextSyncDate(currentTs + interval);
                                                disposable.add(
                                                        interactor.updateChannel(channel).subscribe(aLong -> {
                                                            Log.e("myApp", "finish sync channel = " + channel.getChannelId() + " next sync" + (currentTs + interval));
                                                        })
                                                );
                                            }

                                    )
                            );

                        })
        );
        return Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();
        if (!disposable.isDisposed())
            disposable.dispose();
    }
}
