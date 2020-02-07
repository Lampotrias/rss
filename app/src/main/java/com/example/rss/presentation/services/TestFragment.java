package com.example.rss.presentation.services;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rss.AndroidApplication;
import com.example.rss.R;
import com.example.rss.domain.sync.WorkManagerInteractor;
import com.example.rss.presentation.BaseFragment;

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
        Log.e("myApp", "start");
        disposable.add(
                interactor.getChannelForSync(3600)
                .subscribe(channel -> {
                    disposable.add(interactor.getItemsByChannel(channel)
                    .subscribe(longs -> Log.e("myApp", longs.toString())));
                })
        );
        return rootView;
    }
}
