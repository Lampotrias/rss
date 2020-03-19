package com.example.rss.presentation.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.work.RxWorker;
import androidx.work.WorkerParameters;

import com.example.rss.AndroidApplication;
import com.example.rss.data.cache.FileManager;
import com.example.rss.domain.interactor.ChannelInteractor;
import com.example.rss.domain.interactor.FileInteractor;
import com.example.rss.domain.interactor.ItemInteractor;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class SyncService extends RxWorker {
    private CompositeDisposable disposable;

    private final AndroidApplication application;
    private final Context context;
    @Inject
    public ChannelInteractor channelInteractor;
    @Inject
    public ItemInteractor itemInteractor;
    @Inject
    public FileInteractor fileInteractor;

    @Inject
    public FileManager fileManager;
    SimpleDateFormat formatter;

    private final SharedPreferences preferences;
    private final Long interval;

    @Override
    public void onStopped() {
        super.onStopped();
        application.releaseWorkManagerModule();
        if (!disposable.isDisposed())
            disposable.dispose();
    }

    public SyncService(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        disposable = new CompositeDisposable();
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        interval = Long.parseLong(preferences.getString("sync_time", "0"));

        application = (AndroidApplication) context.getApplicationContext();
        application.getWorkManagerModule().inject(this);

        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
    }

    @NonNull
    @Override
    public Single<Result> createWork() {
        Date date = new Date();
        String curDate = formatter.format(date);

        File logFile = new File (this.context.getCacheDir(), "log.txt");
        fileManager.writeToFile(logFile, curDate + ": " + "start SyncService. Interval = " + interval + "\n");

        return
                channelInteractor.getAllChannels()
                        .doOnSuccess(channels -> {
                            fileManager.writeToFile(logFile, curDate + ": " + "00  " + channels.size() + "\n");
                        })
                        .toObservable()
                        .concatMapIterable(channels -> channels)
                        .filter(channel -> {
                            fileManager.writeToFile(logFile, curDate + ": " + "1  " + channel.getSourceLink() + "\n");
                            Long currentTs = new Date().getTime() / 1000;
                            Long next = channel.getNextSyncDate();
                            Long last = channel.getLastBuild();
                            if (next == 0)
                                return true;

                            fileManager.writeToFile(logFile, curDate + ": " + "22 next = " + next + "\n");
                            fileManager.writeToFile(logFile, curDate + ": " + "33 currentTs = " + currentTs + "\n");
                            if (next < currentTs) { //если запланированная дата уже наступила, то безусловно обновляемся
                                if (last != null && last != 0) {
                                    if (last < next) {
                                        return false;
                                    } else
                                        return next < last && last < currentTs;
                                } else
                                    return true; //нет LastBuild
                            } else
                                return false;
                        })
                        .flatMapMaybe(channel -> channelInteractor.getRawChannel(channel.getSourceLink())
                                .map(xmlChannelRawObject -> {
                                    Long currentTs = new Date().getTime() / 1000;
                                    channel.setTitle(xmlChannelRawObject.getTitle());
                                    channel.setDescription(xmlChannelRawObject.getDescription());
                                    channel.setLink(xmlChannelRawObject.getLink());
                                    channel.setNextSyncDate(currentTs + interval);

                                    if (xmlChannelRawObject.getLastBuild() != null) {
                                        try {
                                            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
                                            Date dateLastSync;
                                            dateLastSync = format.parse(xmlChannelRawObject.getLastBuild());
                                            if (dateLastSync != null) {
                                                channel.setLastBuild(dateLastSync.getTime() / 1000);
                                            }
                                        } catch (ParseException e) {
                                            channel.setLastBuild(0L);
                                        }
                                    } else {
                                        channel.setLastBuild(0L);
                                    }
                                    return channel;
                                    //channel.setFileId(xmlChannelRawObject.getEnclosure());
                                })
                                .flatMap(updChannel -> channelInteractor.update(updChannel)
                                        .map(id -> updChannel))
                        )
                        .flatMap(channel -> channelInteractor.getRssFeedContent(channel.getSourceLink())
                                .flatMap(itemInteractor::parseItemsByStream)
                                .toObservable()
                                .concatMapIterable(xmlItemRawObjects -> xmlItemRawObjects)
                                .filter(xmlItemRawObject -> xmlItemRawObject.getDescription() != null)
                                .concatMap(xmlItemRawObject -> itemInteractor.getItemByUniqueId(xmlItemRawObject.getGuid())
                                        .map(item -> {
                                            xmlItemRawObject.setGuid("");
                                            return xmlItemRawObject;
                                        })
                                        .toObservable().defaultIfEmpty(xmlItemRawObject))
                                .filter(xmlItemRawObject -> !xmlItemRawObject.getGuid().isEmpty())
                                .flatMapMaybe(xmlItemRawObject -> fileInteractor.parseFileAndSave(xmlItemRawObject)
                                        .map(file -> itemInteractor.prepareItem(xmlItemRawObject, file, channel.getChannelId())))
                        )

                        .toList()
                        .doOnSuccess(items -> {
                            fileManager.writeToFile(logFile, curDate + ": " + "44 count = " + items.size() + "\n");
                        })
                        .toMaybe()
                        .flatMap(itemInteractor::insertManyItems)
                        .toSingle()

                        .map(longs -> {
                            Log.e("logo", String.valueOf(longs.size()));
                            return Result.success();
                        });

    }
}
