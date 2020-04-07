package com.example.rss.presentation.itemDetail;

import android.util.Log;

import androidx.navigation.NavController;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.rss.domain.Favorite;
import com.example.rss.domain.exception.DefaultErrorBundle;
import com.example.rss.domain.exception.IErrorBundle;
import com.example.rss.domain.interactor.FileInteractor;
import com.example.rss.domain.interactor.ItemInteractor;
import com.example.rss.presentation.exception.ErrorDetailItemException;
import com.example.rss.presentation.exception.ErrorMessageFactory;
import com.example.rss.presentation.global.GlobalActions;
import com.example.rss.presentation.itemDetail.adapter.DetailViewHolder;
import com.example.rss.presentation.itemDetail.adapter.ViewDetailAdapter;
import com.example.rss.presentation.itemList.adapter.ItemModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class ItemDetailPresenter extends ViewPager2.OnPageChangeCallback implements ItemDetailContract.P<ItemDetailContract.V>, DetailViewHolder.onClick {
    private ItemDetailContract.V mView;
    private final ItemInteractor itemInteractor;
    private final FileInteractor fileInteractor;
    private final CompositeDisposable compositeDisposable;
    private ViewDetailAdapter adapter;
    private Long channelId;
    private Long currentItemId;
    private final int pageSize = 3;
    private int totalItemsCount;
    private int posInChannelQueue;
    private int adapterTmpPosition;

    @Inject
    GlobalActions globalActions;

    @Inject
    NavController navController;

    @Inject
    ItemDetailPresenter(ItemInteractor itemInteractor, FileInteractor fileInteractor) {
        this.itemInteractor = itemInteractor;
        this.fileInteractor = fileInteractor;
        compositeDisposable = new CompositeDisposable();
    }

    private void initRecycler() {
        compositeDisposable.add(
                Maybe
                        .zip(
                                itemInteractor.getPosItemInChannelQueue(this.channelId, this.currentItemId),
                                itemInteractor.getCountItemsByChannel(this.channelId),
                                (position, count) -> {
                                    this.totalItemsCount = count;
                                    this.posInChannelQueue = position;
                                    return true;
                                }
                        )
                        .flatMapSingle(a -> loadInitialPage()
                                .map(items -> {
                                    //real init recycler
                                    RequestManager requestManager = Glide.with(mView.context());
                                    adapter = new ViewDetailAdapter(items, requestManager, this);
                                    mView.getViewPager().registerOnPageChangeCallback(this);
                                    mView.getViewPager().setAdapter(adapter);

                                    int currentAdapterPosition = 0;
                                    for (ItemModel itemModel : items)
                                        if (itemModel.getItemId().equals(this.currentItemId))
                                            currentAdapterPosition = items.indexOf(itemModel);

                                    adapterTmpPosition = currentAdapterPosition;
                                    mView.getViewPager().setCurrentItem(currentAdapterPosition, false);
                                    return true;
                                }))
                        .subscribe(aBoolean ->
                                {
//                                    compositeDisposable.add(
//                                            Single
//                                                    .zip(
//                                                            loadPageLast(),
//                                                            loadPageNext(),
//                                                            (lastItems, nextItems) -> {
//                                                                this.needUpdatePos = false;
//                                                                adapter.insertItemBefore(lastItems);
//                                                                adapter.insertItemAfter(nextItems);
//                                                                this.needUpdatePos = true;
//                                                                return true;
//                                                            })
//                                                    .subscribe()
//                                    );
                                }
                        )
        );
    }

    private Single<List<ItemModel>> loadInitialPage() {
        //return getData(this.channelId, this.posInChannelQueue - 1, 1);
        return getData(this.channelId, 0, 500);
    }

    private Single<List<ItemModel>> loadPageNext() {
        if (!isLast())
            return getData(this.channelId, posInChannelQueue, this.pageSize);
        return Single.just(new ArrayList<>());
    }

    private Single<List<ItemModel>> loadPageLast() {
        int offset, limit;
        if (!isStart()) {
            offset = (posInChannelQueue - this.pageSize - 1);
            limit = this.pageSize;
            if (offset < 0) {
                offset = 0;
                limit = posInChannelQueue - 1;
            }
            return getData(this.channelId, offset, limit);
        }
        return Single.just(new ArrayList<>());
    }

    private boolean isStart() {
        return (this.posInChannelQueue == 1);
    }

    private boolean isLast() {
        return (this.posInChannelQueue + pageSize) >= this.totalItemsCount;
    }

    private Single<List<ItemModel>> getData(Long channelId, Integer offset, Integer limit) {
        return itemInteractor.getItemsWithOffsetByChannel(channelId, offset, limit)
                .toObservable()
                .flatMapIterable(items -> items)
                .concatMapMaybe(item -> fileInteractor.getLinkByFileId(item.getEnclosure()).map(imgPath -> {
                    ItemModel itemModel = ItemModel.transform(item);
                    itemModel.setEnclosure(imgPath);
                    return itemModel;
                }))
                .toList();
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        Log.e("logo", String.valueOf(position));

//        if (position <= 1)
//            compositeDisposable.add(loadPageLast()
//                    .subscribe(itemModels -> {
//                        adapter.insertItemBefore(itemModels);
//                    }));

        if (position != adapterTmpPosition) {
            if (position > this.adapterTmpPosition) {
                this.posInChannelQueue++;
            } else {
                this.posInChannelQueue--;
            }
            this.adapterTmpPosition = position;
        }
        this.setTitleForPosition(position);
        this.updateReadStatus(position);
        this.currentItemId = this.adapter.getItem(position).getItemId();
    }

    private void updateReadStatus(int position) {
        compositeDisposable.add(itemInteractor.updateItemReadById(this.currentItemId, true).subscribe(
                integer -> {},
                throwable -> showErrorMessage(new DefaultErrorBundle((Exception) throwable))
        ));
    }

    private void setTitleForPosition(int position) {
        globalActions.setTitle(this.posInChannelQueue + "/" + this.totalItemsCount + "  " + this.adapter.getItem(position).getTitle());
    }

    @Override
    public void resume() {
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
        currentItemId = mView.getItemId();
        channelId = mView.getChannelId();

        if (currentItemId > 0 && channelId > 0) {
            initRecycler();
        } else {
            showErrorMessage(new DefaultErrorBundle(new ErrorDetailItemException()));
            navController.navigateUp();
        }
    }

    @Override
    public void clickStar() {
        int position = this.adapterTmpPosition;

        Boolean isStar = this.adapter.getItem(position).getStar();
        if (isStar) {
            compositeDisposable.add(itemInteractor.deleteFavByItemBy(this.currentItemId).subscribe(integer -> {
            }, throwable -> showErrorMessage(new DefaultErrorBundle((Exception) throwable))));
        } else {
            Favorite favorite = new Favorite();
            favorite.setItemId(this.currentItemId);
            favorite.setFavId(this.currentItemId);
            favorite.setCategoryId(1L);
            compositeDisposable.add(itemInteractor.insertFavorite(favorite).subscribe(integer -> {
            }, throwable -> showErrorMessage(new DefaultErrorBundle((Exception) throwable))));
        }
        this.adapter.getItem(position).setStar(!isStar);
    }
}