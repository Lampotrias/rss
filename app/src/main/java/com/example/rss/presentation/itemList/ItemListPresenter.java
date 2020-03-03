package com.example.rss.presentation.itemList;

import android.os.Bundle;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.View;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.rss.R;
import com.example.rss.domain.Favorite;
import com.example.rss.domain.Item;
import com.example.rss.domain.exception.DefaultErrorBundle;
import com.example.rss.domain.exception.IErrorBundle;
import com.example.rss.presentation.exception.ErrorMessageFactory;
import com.example.rss.presentation.global.GlobalActions;
import com.example.rss.presentation.itemList.adapter.ItemModel;
import com.example.rss.presentation.itemList.adapter.RecyclerItemClickListener;
import com.example.rss.presentation.itemList.adapter.RecyclerListAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;


public class ItemListPresenter implements ItemListContract.P<ItemListContract.V>, RecyclerListAdapter.SwipeCallback
{
	private ItemListContract.V mView;
	private final ItemListInteractor interactor;
	private final CompositeDisposable cDisposable;
	private Long channelId;
	private RecyclerListAdapter recyclerAdapter;
	private static LongSparseArray<String> channelToImage = new LongSparseArray<>();

	@Inject
	GlobalActions globalActions;

	@Inject
	NavController navController;

	@Inject
	ItemListPresenter(ItemListInteractor interactor) {
		this.interactor = interactor;
		cDisposable = new CompositeDisposable();
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
		if (!cDisposable.isDisposed())
			cDisposable.dispose();
	}

	private Maybe<List<Item>> getItemsByChannels(Long channelId){
		if (channelId > 0)
			return interactor.getItemsByChannelId(channelId);
		return interactor.getAllItems();
	}

	private Observable<ItemModel> prepareItem(Item item, Long channelId) {
		Long realChannelId = item.getChannelId();
		return Observable.fromCallable(() -> transform(item))
				.flatMap(itemModel -> {
					if (channelId == 0) {
						String imgPath = channelToImage.get(realChannelId);

						if (imgPath != null){
							itemModel.setEnclosure(imgPath);
							return Observable.just(itemModel);
						}

						return interactor.getChannelById(realChannelId)
								.flatMap(channel -> interactor.getFileById(channel.getFileId()).map(file -> {
									channelToImage.put(realChannelId, file.getPath());
									itemModel.setEnclosure(file.getPath());
									return itemModel;
								})).defaultIfEmpty(itemModel).toObservable();
					} else {
						itemModel.setEnclosure("");
						return Observable.just(itemModel);
					}
				});
	}

	private Single<List<ItemModel>> getItemsForRecycler(Long channelId){
		return getItemsByChannels(channelId)
				.toObservable()
				.doOnNext(items -> Log.e("myApp", "*++* " + items.size()))
				.concatMapIterable(items -> items)
				.concatMap(item -> prepareItem(item, channelId))
				.doOnNext(item -> Log.e("myApp", "*** " + item.getTitle()))
				.toList();
	}

	private void InitializeRecycler(List<ItemModel> itemModels){
		RequestManager requestManager = Glide.with(mView.context());

		recyclerAdapter = new RecyclerListAdapter(requestManager, mView.getResourceIdRowView(), mView.context());
		recyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
			@Override
			public void onItemRangeInserted(int positionStart, int itemCount) {
				//super.onItemRangeInserted(positionStart, itemCount);
				mView.getRecycler().smoothScrollToPosition(0);
			}
		});
		recyclerAdapter.submitList(itemModels);
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mView.context());
		mView.getRecycler().setLayoutManager(layoutManager);
		mView.getRecycler().setAdapter(recyclerAdapter);
		RecyclerListAdapter.SwipeHelper swipeHelper= recyclerAdapter.new SwipeHelper(
				this,
				mView.context().getDrawable(R.drawable.ic_star_yellow_30dp),
				mView.context().getDrawable(R.drawable.ic_read_black_30dp));
		ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeHelper);
		itemTouchHelper.attachToRecyclerView(mView.getRecycler());
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
		model.setStar((item.getFavorite() == null)?false:item.getFavorite());
		model.setRead((item.getRead() == null)?false:item.getRead());
		return model;
	}

	@Override
	public void setView(ItemListContract.V view) {
		this.mView = view;
		this.channelId = mView.getCurChannelId();

		cDisposable.add(getItemsForRecycler(this.channelId)
				.subscribe(this::InitializeRecycler, throwable -> {}));
	}

	@Override
	public void refreshList() {
		cDisposable.add(interactor.syncGetItems(channelId).subscribe(
				longs -> {

				},
				throwable -> {
				},
				() -> {
					cDisposable.add(getItemsForRecycler(this.channelId)
							.subscribe(itemModels -> {
										recyclerAdapter.submitList(itemModels);
										mView.stopRefresh();
									}
									, throwable -> {
										showErrorMessage(new DefaultErrorBundle((Exception) throwable));
										mView.stopRefresh();
									}));
				})
		);
	}
	@Override
	public void onSwiped(Long  itemId, int direction, Boolean value) {
		if (direction == RecyclerListAdapter.SWIPE_FAVORITE){
			if (value){
				Favorite favorite = new Favorite();
				favorite.setItemId(itemId);
				favorite.setFavId(itemId);
				favorite.setCategoryId(1L);
				cDisposable.add(interactor.insertFavorite(favorite).subscribe(()-> {}));
			}else
				interactor.deleteFavByItemBy(itemId).subscribe(()-> {});
		}else if(direction == RecyclerListAdapter.SWIPE_READ){
			cDisposable.add(interactor.updateReadById(itemId, value).subscribe(()-> {}));
		}
	}
}
