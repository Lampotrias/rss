package com.example.rss.presentation.channelEdit;


import android.content.Context;
import android.util.Log;

import androidx.navigation.NavController;
import androidx.room.EmptyResultSetException;

import com.example.rss.domain.exception.DefaultErrorBundle;
import com.example.rss.domain.exception.IErrorBundle;
import com.example.rss.presentation.exception.ChannelExistsException;
import com.example.rss.presentation.exception.ErrorMessageFactory;
import com.example.rss.presentation.global.GlobalActions;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class ChannelEditPresenter implements ChannelEditContract.P<ChannelEditContract.V>
{
	private static final String LOG_TAG = ChannelEditPresenter.class.getSimpleName();
	private ChannelEditContract.V mView;
	private final ChannelEditInteractor channelEditInteractor;
	private final CompositeDisposable compositeDisposable;
	protected Context context;
	private String sourceUrl;

	@Inject
	GlobalActions globalActions;

	@Inject
	NavController navController;
	@Inject
	ChannelEditPresenter(ChannelEditInteractor channelEditInteractor) {
		this.channelEditInteractor = channelEditInteractor;
		compositeDisposable = new CompositeDisposable();
	}

	@Override
	public void onSaveButtonClicked(String url, Boolean bCacheImage, Boolean bDownloadFull, Boolean bOnlyWifi){
		//ToDo addChannel category logic
		mView.disableBtnAdd(true);
		compositeDisposable.add(
				channelEditInteractor.checkChannelExistsByUrl(url)
					.subscribe(channel -> {
						showErrorMessage(new DefaultErrorBundle(new ChannelExistsException()));
						mView.disableBtnAdd(false);
					}, throwable -> {
							if (throwable instanceof EmptyResultSetException){
								compositeDisposable.add(channelEditInteractor.addChannel(url, 1L, bCacheImage, bDownloadFull, bOnlyWifi)
										.subscribe(
												aLong -> {
														mView.displaySuccess("new id: " + aLong);
														mView.disableBtnAdd(false);
														globalActions.updDrawerMenu();

													},
												throwable1 -> showErrorMessage(new DefaultErrorBundle((Exception) throwable1))));
							}else{
								showErrorMessage(new DefaultErrorBundle((Exception) throwable));
							}
							mView.disableBtnAdd(false);
						}
		));

	}

	@Override
	public void onCancelButtonClicked() {
		navController.navigateUp();
	}

	@Override
	public void setView(ChannelEditContract.V view) {
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
		Log.e("myApp", "ChannelEdit resume");
		globalActions.setTitle("Add channel");
	}

	@Override
	public void pause() {
		Log.e("myApp", "ChannelEdit pause");
	}

	@Override
	public void destroy() {
		Log.e("myApp", "ChannelEdit destroy");
		if (!compositeDisposable.isDisposed())
			compositeDisposable.dispose();
	}
}
