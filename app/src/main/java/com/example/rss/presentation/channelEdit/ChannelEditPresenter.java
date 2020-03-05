package com.example.rss.presentation.channelEdit;


import android.content.Context;
import android.util.Log;

import androidx.navigation.NavController;
import androidx.room.EmptyResultSetException;

import com.example.rss.domain.Category;
import com.example.rss.domain.exception.DefaultErrorBundle;
import com.example.rss.domain.exception.IErrorBundle;
import com.example.rss.domain.interactor.CategoryInteractor;
import com.example.rss.domain.interactor.ChannelInteractor;
import com.example.rss.domain.interactor.FileInteractor;
import com.example.rss.presentation.exception.ChannelExistsException;
import com.example.rss.presentation.exception.ErrorMessageFactory;
import com.example.rss.presentation.global.GlobalActions;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class ChannelEditPresenter implements ChannelEditContract.P<ChannelEditContract.V>
{
	private static final String LOG_TAG = ChannelEditPresenter.class.getSimpleName();
	private ChannelEditContract.V mView;
	private final ChannelInteractor channelInteractor;
	private final CategoryInteractor categoryInteractor;
	private final FileInteractor fileInteractor;
	private final CompositeDisposable compositeDisposable;
	protected Context context;
	private String sourceUrl;

	@Inject
	GlobalActions globalActions;

	@Inject
	NavController navController;
	@Inject
	ChannelEditPresenter(ChannelInteractor channelInteractor, CategoryInteractor categoryInteractor, FileInteractor fileInteractor) {
		this.channelInteractor = channelInteractor;
		this.categoryInteractor = categoryInteractor;
		this.fileInteractor = fileInteractor;
		compositeDisposable = new CompositeDisposable();
	}

	@Override
	public void onSaveButtonClicked(String url, Boolean bCacheImage, Boolean bDownloadFull, Boolean bOnlyWifi) {
		//ToDo addChannelAndFile category logic
		mView.isEnable(false);
		compositeDisposable.add(
				categoryInteractor.getCategoryById(1L)
						.subscribe(category -> {
									compositeDisposable.add(channelInteractor.checkChannelExistsByUrl(url)
											.subscribe(channel -> {
														showErrorMessage(new DefaultErrorBundle(new ChannelExistsException()));
														mView.isEnable(true);
													}, throwable -> {
														if (throwable instanceof EmptyResultSetException) {
															compositeDisposable.add(channelInteractor.getRawChannel(url)
																	.flatMap(rawObject -> fileInteractor.parseFileAndSave(rawObject)
																			.map(fileId -> channelInteractor.prepareChannelObj(rawObject, url, fileId, category.getCategoryId(), bCacheImage, bDownloadFull, bOnlyWifi)))
																	.flatMap(channelInteractor::add)
																	.subscribe(
																			aLong -> {
																				mView.displaySuccess("new id: " + aLong);
																				mView.isEnable(true);
																				globalActions.updDrawerMenu();
																				navController.navigateUp();
																			},
																			throwableChannelAdd -> showErrorMessage(new DefaultErrorBundle((Exception) throwableChannelAdd))));
														} else {
															showErrorMessage(new DefaultErrorBundle((Exception) throwable));
														}
														mView.isEnable(true);
													}
											));
								}, throwable -> {
								},
								() -> {
									Category category = new Category();
									category.setType("C");
									category.setName("Без категории");
									category.setCategoryId(1L);
									compositeDisposable.add(categoryInteractor.addCategory(category)
											.subscribe(aLong -> onSaveButtonClicked(url, bCacheImage, bDownloadFull, bOnlyWifi)));
								}));
	}

	@Override
	public void onCancelButtonClicked() {
		navController.navigateUp();
	}

	@Override
	public void setView(ChannelEditContract.V view) {
		mView = view;
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
