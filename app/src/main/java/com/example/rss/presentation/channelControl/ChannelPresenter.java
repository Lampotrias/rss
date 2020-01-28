package com.example.rss.presentation.channelControl;


import android.content.Context;
import android.util.Log;

import androidx.room.EmptyResultSetException;

import com.example.rss.data.exception.DatabaseConnectionException;
import com.example.rss.domain.exception.DefaultErrorBundle;
import com.example.rss.domain.exception.IErrorBundle;
import com.example.rss.presentation.exception.ChannelExistsException;
import com.example.rss.presentation.exception.ChannelNotFoundException;
import com.example.rss.presentation.exception.ErrorMessageFactory;
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
	ChannelPresenter(ChannelInteractor channelInteractor) {
		this.channelInteractor = channelInteractor;
		compositeDisposable = new CompositeDisposable();
	}


	@Override
	public void onSaveButtonClicked(String url, Boolean bCacheImage, Boolean bDownloadFull, Boolean bOnlyWifi){
		//ToDo addChannel category logic

		compositeDisposable.add(
				channelInteractor.checkChannelExistsByUrl(url)
					.subscribe(channel -> {
							showErrorMessage(new DefaultErrorBundle(new ChannelExistsException()));
						},throwable -> {
							if (throwable instanceof EmptyResultSetException){
								compositeDisposable.add(channelInteractor.addChannel(url, 1L, bCacheImage, bDownloadFull, bOnlyWifi)
										.subscribe(
												aLong -> {
													mView.displaySuccess("new id: " + aLong);
												}, throwableAdd -> {
													showErrorMessage(new DefaultErrorBundle((Exception) throwableAdd));
												}));
							}else{
								showErrorMessage(new DefaultErrorBundle((Exception) throwable));
							}
						}
		));

	}

	@Override
	public void onCancelButtonClicked() {
		compositeDisposable.add(
				channelInteractor.getChannelById(1L)
						.subscribe((channel, throwable) -> {
							Log.e("myApp", "1123");
						})
		);
	}

	@Override
	public void setView(ChannelContract.V view) {
		mView = view;
	}

	public void addNewChannel(String url) {

	}

	private void showErrorMessage(IErrorBundle errorBundle) {
		String errorMessage = ErrorMessageFactory.create(this.mView.context(), errorBundle.getException());
		mView.displayError(errorMessage);
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
