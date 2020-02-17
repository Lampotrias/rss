package com.example.rss.presentation.itemList;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
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
import com.example.rss.presentation.itemList.adapter.RecyclerItemClickListener;
import com.example.rss.presentation.itemList.adapter.RecyclerListPresenter;
import com.example.rss.presentation.itemList.adapter.RecyclerListAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.disposables.CompositeDisposable;


public class ItemListPresenter implements ItemListContract.P<ItemListContract.V>
{
	private ItemListContract.V mView;
	private final ItemListInteractor itemListInteractor;
	private final CompositeDisposable compositeDisposable;
	private RecyclerListPresenter recyclerListPresenter;

	@Inject
	GlobalActions globalActions;

	@Inject
	NavController navController;

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

	private Maybe<List<Item>> switchChannel(Long channelId){
		if (channelId > 0)
			return itemListInteractor.getItemsByChannelId(channelId);
		return itemListInteractor.getAllItems();
	}

	void initRecycler(Long channelId){
		List<ItemModel> itemModels= new ArrayList<>();
		compositeDisposable.add(switchChannel(channelId)
				.toFlowable()
				.concatMapIterable(items -> items)
				.subscribe(item -> {
					ItemModel itemModel = transform(item);
					compositeDisposable.add(
							itemListInteractor.getFileById(item.getEnclosure())
							.subscribe(file -> itemModel.setEnclosure(file.getPath()), throwable -> Log.e("myApp", "error")));
					itemModels.add(itemModel);
				}, throwable -> showErrorMessage(new DefaultErrorBundle((Exception) throwable)),
						() -> {
							RequestManager requestManager = Glide.with(mView.context());
							recyclerListPresenter = new RecyclerListPresenter(requestManager, mView.getResourceIdRowView());
							RecyclerListAdapter recyclerAdapter = new RecyclerListAdapter(recyclerListPresenter);
							recyclerListPresenter.setAdapter(recyclerAdapter);
							recyclerListPresenter.submitList(itemModels);
							RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mView.context());

							mView.getRecycler().setLayoutManager(layoutManager);
							mView.getRecycler().setAdapter(recyclerAdapter);

							mView.getRecycler().addOnItemTouchListener(new RecyclerItemClickListener(null, mView.getRecycler(), new RecyclerItemClickListener.OnItemClickListener() {
								@Override
								public void onItemClick(View view, int position) {
									Bundle bundle = new Bundle();
									bundle.putInt("DETAIL_POSITION", position);
									bundle.putLong("DETAIL_CHANNEL_ID", channelId);
									navController.navigate(R.id.nav_itemDetailFragment, bundle);
								}

								@Override
								public void onLongItemClick(View view, int position) {

								}
							}));
				})
		);

	}

	private void showErrorMessage(IErrorBundle errorBundle) {
		String errorMessage = ErrorMessageFactory.create(this.mView.context(), errorBundle.getException());
		mView.displayError(errorMessage);
	}

	private ItemModel transform(Item item){
		DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());

		ItemModel model = new ItemModel();
		model.setItemId(item.getItemId());
		model.setGuid(item.getGuid());
		model.setTitle(item.getTitle());
		model.setDescription(item.getDescription());
		model.setLink(item.getLink());
		model.setPubDate(format.format(item.getPubDate()));
		model.setRead(item.getRead());
		model.setStar(item.getFavorite());
		return model;
	}

	@Override
	public void setView(ItemListContract.V view) {
		this.mView = view;
		initRecycler(mView.getCurChannelId());
	}

	@Override
	public void refreshList() {
		compositeDisposable.add(itemListInteractor.deleteAllChannels().subscribe(() -> {
			compositeDisposable.add(itemListInteractor.deleteAllItems().subscribe(() -> {
				mView.stopRefresh();
				globalActions.updDrawerMenu();
			}, throwable -> mView.stopRefresh()));
		}, throwable -> mView.stopRefresh()));
		//mView.stopRefresh();
	}
}
