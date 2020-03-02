package com.example.rss.presentation.services;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.EmptyResultSetException;

import com.example.rss.AndroidApplication;
import com.example.rss.R;
import com.example.rss.domain.sync.WorkManagerInteractor;
import com.example.rss.presentation.BaseFragment;

import java.util.Date;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class TestFragment extends BaseFragment {

    @Inject
    WorkManagerInteractor interactor;

    CompositeDisposable disposable = new CompositeDisposable();
    AndroidApplication app;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (AndroidApplication) Objects.requireNonNull(getActivity()).getApplication();
        app.getFragmentModule(this).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.view.View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        disposable.add(
                interactor.getChannelForSync(0)
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
                                                channel.setNextSyncDate(currentTs + 0);
                                                disposable.add(
                                                        interactor.updateNextExec(channel.getChannelId(), currentTs).subscribe(aLong -> {
                                                            Log.e("myApp", "finish sync channel = " + channel.getChannelId() + " next sync" + (currentTs + 3600));
                                                        })
                                                );
                                            }

                                    )
                            );

                        })
        );
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        disposable.add(interactor.getChannel(1L).subscribe(channel -> {
            Log.e("logo", channel.getTitle());
        }));
    }
}
