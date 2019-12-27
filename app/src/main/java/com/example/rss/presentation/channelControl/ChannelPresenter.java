package com.example.rss.presentation.channelControl;


import android.content.Context;

import com.example.rss.domain.interactor.DefaultObserver;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class ChannelPresenter implements ChannelContract.P<ChannelContract.V>
{
	private static final String LOG_TAG = ChannelPresenter.class.getSimpleName();
	private ChannelContract.V mView;
	private final CInteractor cInteractor;
	private final CompositeDisposable compositeDisposable;
	protected Context context;
	private String sourceUrl;

	@Inject
	public ChannelPresenter(CInteractor cInteractor) {
		this.cInteractor = cInteractor;
		compositeDisposable = new CompositeDisposable();
	}


	@Override
	public void setView(ChannelContract.V view) {
		mView = view;
	}

	@Override
	public void ShowExitsChannel(int id) {

	}

	public void addNewChannel(String url) {
		compositeDisposable.add(cInteractor.add(url)
		.subscribe(s -> {
			//ok
				}, throwable -> {
			//error
				}, () -> {
			//Success
				}
		));

		//channelInteractor.execute(new ReturnObserver(), url);
	}

	@Override
	public void resume() {
		mView.displaySuccess("Init Ok");
	}

	@Override
	public void pause() {

	}

	@Override
	public void destroy() {

	}

	final class ReturnObserver extends DefaultObserver<Void>{
		@Override
		public void onNext(Void aVoid) {
			super.onNext(aVoid);
		}

		@Override
		public void onError(Throwable e) {
			super.onError(e);
		}

		@Override
		public void onComplete() {
			super.onComplete();
		}
	}

}
