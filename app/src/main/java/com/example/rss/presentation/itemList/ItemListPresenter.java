package com.example.rss.presentation.itemList;


import android.content.Context;
import android.util.Log;

import androidx.room.EmptyResultSetException;

import com.example.rss.domain.exception.DefaultErrorBundle;
import com.example.rss.domain.exception.IErrorBundle;
import com.example.rss.presentation.channelEdit.ChannelEditContract;
import com.example.rss.presentation.exception.ChannelExistsException;
import com.example.rss.presentation.exception.ErrorMessageFactory;
import com.example.rss.presentation.global.GlobalActions;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class ItemListPresenter implements ItemListContract.P<ItemListContract.V>
{
	private ItemListContract.V mView;
	private final ItemListInteractor itemListInteractor;
	private final CompositeDisposable compositeDisposable;


	@Inject
	GlobalActions globalActions;

	@Inject
	ItemListPresenter(ItemListInteractor itemListInteractor) {
		this.itemListInteractor = itemListInteractor;
		compositeDisposable = new CompositeDisposable();
	}


	@Override
	public void resume() {
		//globalActions.setTitle("");
	}

	@Override
	public void pause() {

	}

	@Override
	public void destroy() {
		if (!compositeDisposable.isDisposed())
			compositeDisposable.dispose();
	}

	@Override
	public void setView(ItemListContract.V view) {

	}
}
