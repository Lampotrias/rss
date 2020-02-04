package com.example.rss.presentation.itemList;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.rss.R;
import com.example.rss.domain.File;
import com.example.rss.domain.Item;
import com.example.rss.domain.exception.DefaultErrorBundle;
import com.example.rss.domain.exception.IErrorBundle;
import com.example.rss.presentation.exception.ErrorMessageFactory;
import com.example.rss.presentation.global.GlobalActions;
import com.example.rss.presentation.itemList.adapter.ItemModel;
import com.example.rss.presentation.itemList.adapter.RepositoriesListPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class ItemListPresenter implements ItemListContract.P<ItemListContract.V>
{
	private ItemListContract.V mView;
	private final ItemListInteractor itemListInteractor;
	private final CompositeDisposable compositeDisposable;
	private RepositoriesListPresenter repositoriesListPresenter;

	@Inject
	GlobalActions globalActions;

	@Inject
	ItemListPresenter(ItemListInteractor itemListInteractor) {
		this.itemListInteractor = itemListInteractor;
		compositeDisposable = new CompositeDisposable();
	}

	@Override
	public void resume() {
		globalActions.setTitle(mView.context().getString(R.string.all_items));
	}

	@Override
	public void pause() {

	}

	@Override
	public void destroy() {
		if (!compositeDisposable.isDisposed())
			compositeDisposable.dispose();
	}

	void initRecycler(){
		compositeDisposable.add(itemListInteractor.getItemsByChannelId(1L)
				.flatMapIterable(items -> items)
				.flatMap(item -> itemListInteractor.getFileById(item.getEnclosure()).toFlowable().map(file -> transform(item, file)))
				.toList()
				.subscribe(itemModels -> {
					RequestManager requestManager = Glide.with(mView.context());
					repositoriesListPresenter = new RepositoriesListPresenter(requestManager, itemModels);
				}, throwable -> showErrorMessage(new DefaultErrorBundle((Exception) throwable)))
		);

	}

	private void showErrorMessage(IErrorBundle errorBundle) {
		String errorMessage = ErrorMessageFactory.create(this.mView.context(), errorBundle.getException());
		mView.displayError(errorMessage);
	}

	private ItemModel transform(Item item, File file){
		ItemModel model = new ItemModel();
		model.setItemId(item.getItemId());
		model.setGuid(item.getGuid());
		model.setTitle(item.getTitle());
		model.setDescription(item.getDescription());
		model.setLink(item.getLink());
		model.setPubDate(item.getPubDate());
		model.setRead(item.getRead());
		model.setStar(item.getFavorite());
		if (file != null)
			model.setEnclosure(file.getPath());
		else
			model.setEnclosure(null);

		return model;
	}

	@Override
	public void setView(ItemListContract.V view) {
		this.mView = view;
		initRecycler();
	}

	@Override
	public void refreshList() {



		mView.stopRefresh();
	}
}
