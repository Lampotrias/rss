package com.example.rss.presentation.global;

import android.view.View;

import com.example.rss.presentation.channelControl.ChannelFragment;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class GlobalPresenter implements GlobalContract.P<GlobalContract.V> {

    private GlobalContract.V mView;
    private GlobalInteractor globalInteractor;
    private CompositeDisposable compositeDisposable;

    @Inject
    GlobalActions globalActions;

    @Inject
    public GlobalPresenter(GlobalInteractor globalInteractor) {
        this.globalInteractor = globalInteractor;
        compositeDisposable = new CompositeDisposable();
//
    }

    @Override
    public void setView(GlobalContract.V view) {
        mView = view;
    }

    @Override
    public void OnClickChannelAdd(View view) {
        globalActions.replaceFragment(ChannelFragment.getInstance());
        mView.closeDrawer();
    }

    @Override
    public void resume() {
        globalActions.setTitle("Main fragment");
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
}
