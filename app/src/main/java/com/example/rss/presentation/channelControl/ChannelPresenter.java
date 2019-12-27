package com.example.rss.presentation.channelControl;


import android.content.Context;

import com.example.rss.domain.interactor.ChannelInteractor;
import com.example.rss.domain.interactor.DefaultObserver;

import javax.inject.Inject;


public class ChannelPresenter implements ChannelContract.P<ChannelContract.V>
{
	private static final String LOG_TAG = ChannelPresenter.class.getSimpleName();
	private ChannelContract.V mView;
	private final ChannelInteractor channelInteractor;
	protected Context context;
	private String sourceUrl;

	@Inject
	public ChannelPresenter(ChannelInteractor channelInteractor) {
		this.channelInteractor = channelInteractor;
	}


	@Override
	public void setView(ChannelContract.V view) {
		mView = view;
	}

	@Override
	public void ShowExitsChannel(int id) {

	}

	public void addNewChannel(String url) {
		channelInteractor.execute(new ReturnObserver(), url);
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

	final class ReturnObserver extends DefaultObserver<Integer>{

		@Override
		public void onNext(Integer integer) {
			mView.displaySuccess("Ok: " + integer.toString());
		}

		@Override
		public void onError(Throwable e) {
			mView.displayError(e);
		}

		@Override
		public void onComplete() {
			super.onComplete();
		}
	}
}
