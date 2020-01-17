package com.example.rss.presentation.channelControl;


import android.content.Context;

import com.example.rss.presentation.global.GlobalActions;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class ChannelPresenter implements ChannelContract.P<ChannelContract.V>
{
	private static final String LOG_TAG = ChannelPresenter.class.getSimpleName();
	private ChannelContract.V mView;
	private final ChannelInteractor channelInteractor;
	private final CompositeDisposable compositeDisposable;
	protected Context context;
	private String sourceUrl;

	@Inject
	GlobalActions globalActions;

	@Inject
	public ChannelPresenter(ChannelInteractor channelInteractor) {
		this.channelInteractor = channelInteractor;
		compositeDisposable = new CompositeDisposable();
	}


	@Override
	public void onSaveButtonClicked(String url, Boolean bCacheImage, Boolean bDownloadFull, Boolean bOnlyWifi){
		//ToDo addChannel category logic
		compositeDisposable.add(channelInteractor.addChannel(url, 1L, bCacheImage, bDownloadFull, bOnlyWifi)
				.subscribe(
						aLong -> {
							mView.displaySuccess("new id: " + aLong);
						}, throwable -> {
							mView.displayError(throwable);
						}));
	}

	@Override
	public void onCancelButtonClicked() {

	}

	@Override
	public void setView(ChannelContract.V view) {
		mView = view;
	}

	public void addNewChannel(String url) {

	}

	@Override
	public void resume() {
		globalActions.setTitle("Add channel");
	}

	@Override
	public void pause() {

	}

	@Override
	public void destroy() {
		if (!compositeDisposable.isDisposed())
			compositeDisposable.dispose();
	}
}
