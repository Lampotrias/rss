package com.example.rss.presentation.channelControl;


import android.content.Context;

import com.example.rss.domain.interactor.ChannelInteractor;
import com.example.rss.domain.interactor.DefaultObserver;
import com.example.rss.presentation.BasePresenter;

import javax.inject.Inject;


public class ChannelPresenter extends BasePresenter implements ChannelContract.Presenter
{
	private static final String LOG_TAG = ChannelPresenter.class.getSimpleName();
	private ChannelContract.View mView;
	private final ChannelInteractor channelInteractor;
	protected Context context;
	private String sourceUrl;

	@Inject
	public ChannelPresenter(ChannelInteractor channelInteractor) {
		this.channelInteractor = channelInteractor;
	}

	@Override
	public void setView(ChannelContract.View view) {
		mView = view;
	}

	@Override
	public void ShowExitsChannel(int id) {

	}

	public void addNewChannel(String url) {
		channelInteractor.execute(new ReturnObserver(), url);
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
