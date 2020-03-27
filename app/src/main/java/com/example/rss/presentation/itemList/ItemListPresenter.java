package com.example.rss.presentation.itemList;

import android.os.Bundle;
import android.util.LongSparseArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import com.example.rss.domain.interactor.ChannelInteractor;
import com.example.rss.domain.interactor.FileInteractor;
import com.example.rss.domain.interactor.ItemInteractor;
import com.example.rss.presentation.exception.ErrorMessageFactory;
import com.example.rss.presentation.global.GlobalActions;
import com.example.rss.presentation.itemDetail.ItemDetailFragment;
import com.example.rss.presentation.itemList.adapter.ItemModel;
import com.example.rss.presentation.itemList.adapter.RecyclerItemClickListener;
import com.example.rss.presentation.itemList.adapter.RecyclerListAdapter;
import com.example.rss.domain.paginator.Paginator;
import com.example.rss.presentation.itemList.adapter.RecyclerViewPaginator;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;


public class ItemListPresenter implements
        ItemListContract.P<ItemListContract.V>,
        RecyclerListAdapter.SwipeCallback,
        Paginator.ViewController<ItemModel> {

    private ItemListContract.V mView;
    private final ChannelInteractor channelInteractor;
    private final ItemInteractor itemInteractor;
    private final FileInteractor fileInteractor;
    private final CompositeDisposable cDisposable;
    private Long channelId;
    private RecyclerListAdapter recyclerAdapter;
    private static LongSparseArray<String> channelToImage = new LongSparseArray<>();
    private final static Integer PAGE_SIZE = 5;

    private LinearLayoutManager layoutManager;
    private RecyclerViewPaginator paginator;

    @Inject
    GlobalActions globalActions;

    @Inject
    NavController navController;

    @Inject
    ItemListPresenter(ChannelInteractor channelInteractor, ItemInteractor itemInteractor, FileInteractor fileInteractor) {
        this.channelInteractor = channelInteractor;
        this.itemInteractor = itemInteractor;
        this.fileInteractor = fileInteractor;
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
        paginator.release();
    }

    private Observable<ItemModel> convertToModel(Item item, Long channelId) {
        Long realChannelId = item.getChannelId();
        return Observable.fromCallable(() -> ItemModel.transform(item))
                .flatMap(itemModel -> {
                    if (channelId == 0) {
                        String imgPath = channelToImage.get(realChannelId);

                        if (imgPath != null) {
                            itemModel.setEnclosure(imgPath);
                            return Observable.just(itemModel);
                        }

                        return channelInteractor.getChannelById(realChannelId)
                                .flatMap(channel -> fileInteractor.getFileById(channel.getFileId()).map(file -> {
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

    private void InitializeRecycler() {
        mView.getRecycler().setItemAnimator(new DefaultItemAnimator());
        RequestManager requestManager = Glide.with(mView.context());

        recyclerAdapter = new RecyclerListAdapter(requestManager, mView.context());

        layoutManager = new LinearLayoutManager(mView.context());
        mView.getRecycler().setLayoutManager(layoutManager);
        mView.getRecycler().setAdapter(recyclerAdapter);

        RecyclerListAdapter.SwipeHelper swipeHelper = recyclerAdapter.new SwipeHelper(
                this,
                mView.context().getDrawable(R.drawable.ic_star_yellow_30dp),
                mView.context().getDrawable(R.drawable.ic_read_black_30dp));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeHelper);
        itemTouchHelper.attachToRecyclerView(mView.getRecycler());

        mView.getRecycler().addOnItemTouchListener(new RecyclerItemClickListener(null, mView.getRecycler(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                ItemModel item = recyclerAdapter.get(position);
                bundle.putLong(ItemDetailFragment.DETAIL_ITEM_ID, item.getItemId());
                bundle.putLong(ItemDetailFragment.DETAIL_CHANNEL_ID, item.getChannelId());
                navController.navigate(R.id.nav_itemDetailFragment, bundle);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        mView.getRecycler().addOnScrollListener(new ScrollingHandler());
    }

    private class ScrollingHandler extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 1
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= PAGE_SIZE) {
                paginator.loadNewPage();
            }
        }
    }

    private void showErrorMgs(IErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.mView.context(), errorBundle.getException());
        mView.displayError(errorMessage);
    }

    @Override
    public void setView(ItemListContract.V view) {
        this.mView = view;
        this.channelId = mView.getCurChannelId();

        InitializeRecycler();
        if (mView.isFavoriteMode())
            paginator = new RecyclerViewPaginator(new FavoritesListPaginator(), this);
        else
            paginator = new RecyclerViewPaginator(new SimpleListPaginator(), this);

        paginator.refresh();
    }

    @Override
    public void refreshList() {
        cDisposable.add(
                channelInteractor.switchChannelSource(this.channelId)
                        .filter(channels -> channels.size() > 0)
                        .concatMapIterable(channels -> channels)
                        .flatMap(channel -> channelInteractor.getRssFeedContent(channel.getSourceLink())
                                .flatMap(itemInteractor::parseItemsByStream)
                                .toObservable()
                                .concatMapIterable(xmlItemRawObjects -> xmlItemRawObjects)
                                .filter(xmlItemRawObject -> xmlItemRawObject.getDescription() != null)
                                .concatMap(xmlItemRawObject -> itemInteractor.getItemByUniqueId(xmlItemRawObject.getGuid())
                                        .map(item -> {
                                            xmlItemRawObject.setGuid("");
                                            return xmlItemRawObject;
                                        })
                                        .toObservable().defaultIfEmpty(xmlItemRawObject))
                                .filter(xmlItemRawObject -> !xmlItemRawObject.getGuid().isEmpty())
                                .flatMapMaybe(xmlItemRawObject -> fileInteractor.parseFileAndSave(xmlItemRawObject)
                                        .map(file -> itemInteractor.prepareItem(xmlItemRawObject, file, channel.getChannelId())))
                        )
                        .toList()
                        .toMaybe()
                        .flatMap(itemInteractor::insertManyItems)
                        .subscribe(
                                insertItems -> {
                                    paginator.refresh();
                                    mView.stopRefresh();
                                },
                                throwable -> {
                                    mView.stopRefresh();
                                }, () -> {
                                    mView.setEmptyView(false);
                                    mView.stopRefresh();
                                })
        );
    }

    @Override
    public void onSwiped(Long itemId, int direction, Boolean value) {
        if (direction == RecyclerListAdapter.SWIPE_FAVORITE) {
            if (value) {
                Favorite favorite = new Favorite();
                favorite.setItemId(itemId);
                favorite.setFavId(itemId);
                favorite.setCategoryId(1L);
                cDisposable.add(itemInteractor.insertFavorite(favorite).subscribe(aLong -> {
                }));
            } else
                cDisposable.add(itemInteractor.deleteFavByItemBy(itemId).subscribe(integer -> {
                }));
        } else if (direction == RecyclerListAdapter.SWIPE_READ) {
            cDisposable.add(itemInteractor.updateItemReadById(itemId, value).subscribe(integer -> {
            }));
        }
    }

    @Override
    public void showData(Boolean show, List<ItemModel> data) {
        recyclerAdapter.submitList(data);
    }

    @Override
    public void showErrorMessage(Throwable error) {
        showErrorMgs(new DefaultErrorBundle((Exception) error));
    }

    @Override
    public void showEmptyError(Boolean show, Throwable error) {
        showErrorMgs(new DefaultErrorBundle((Exception) error));
    }

    @Override
    public void showRefreshProgress(Boolean show) {

    }

    @Override
    public void showEmptyProgress(Boolean show) {

    }

    @Override
    public void showPageProgress(Boolean show) {

    }

    @Override
    public void showEmptyView(Boolean show) {
        mView.setEmptyView(show);
    }

    class SimpleListPaginator implements Paginator.Page<ItemModel> {
        @Override
        public Maybe<List<ItemModel>> invoke(int page) {
            int offset = (page == 1) ? 0 : (page - 1) * PAGE_SIZE;

            return itemInteractor.getItemsWithOffsetByChannel(ItemListPresenter.this.channelId, offset, PAGE_SIZE)
                    .toObservable()
                    .concatMapIterable(items -> items)
                    .concatMap(item -> convertToModel(item, channelId))
                    .toList()
                    .toMaybe();
        }
    }

    class FavoritesListPaginator implements Paginator.Page<ItemModel>{
        @Override
        public Maybe<List<ItemModel>> invoke(int page) {
            int offset = (page == 1) ? 0 : (page - 1) * PAGE_SIZE;
            return itemInteractor.getFavoritesWithOffset(offset, PAGE_SIZE)
                    .toObservable()
                    .concatMapIterable(items -> items)
                    .concatMap(item -> convertToModel(item, channelId))
                    .toList()
                    .toMaybe();
        }
    }
}
