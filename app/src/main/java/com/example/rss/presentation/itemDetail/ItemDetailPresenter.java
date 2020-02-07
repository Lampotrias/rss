package com.example.rss.presentation.itemDetail;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.rss.R;
import com.example.rss.domain.Item;
import com.example.rss.domain.exception.DefaultErrorBundle;
import com.example.rss.domain.exception.IErrorBundle;
import com.example.rss.presentation.exception.ErrorMessageFactory;
import com.example.rss.presentation.global.GlobalActions;
import com.example.rss.presentation.itemList.adapter.ItemModel;
import com.example.rss.presentation.itemList.adapter.RepositoriesListPresenter;
import com.example.rss.presentation.itemList.adapter.RepositoriesRecyclerAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class ItemDetailPresenter implements ItemDetailContract.P<ItemDetailContract.V>
{
	private ItemDetailContract.V mView;
	private final ItemDetailInteractor ItemDetailInteractor;
	private final CompositeDisposable compositeDisposable;

	@Inject
	GlobalActions globalActions;

	@Inject
	ItemDetailPresenter(ItemDetailInteractor itemListInteractor) {
		this.ItemDetailInteractor = itemListInteractor;
		compositeDisposable = new CompositeDisposable();
	}

	@Override
	public void resume() {
		globalActions.setTitle("");
	}

	@Override
	public void pause() {

	}

	@Override
	public void destroy() {
		if (!compositeDisposable.isDisposed())
			compositeDisposable.dispose();
	}



	private void showErrorMessage(IErrorBundle errorBundle) {
		String errorMessage = ErrorMessageFactory.create(this.mView.context(), errorBundle.getException());
		mView.displayError(errorMessage);
	}

	@Override
	public void setView(ItemDetailContract.V view) {
		this.mView = view;
	}
}
