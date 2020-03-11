package com.example.rss.presentation.itemDetail;

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

import io.reactivex.disposables.CompositeDisposable;


public class ItemDetailPresenter extends ViewPager2.OnPageChangeCallback implements ItemDetailContract.P<ItemDetailContract.V>, DetailViewHolder.onClick {
    private ItemDetailContract.V mView;
    private final ItemInteractor itemInteractor;
    private final FileInteractor fileInteractor;
    private final CompositeDisposable compositeDisposable;
    private ViewDetailAdapter viewDetailAdapter;
    private List<ItemModel> itemModels = new ArrayList<>();
    private Long channelId;
    private Long itemId;
    private final Integer pageSize = 2;
    private Integer currentAbsPosition;
    private Integer totalItemsCount;
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
        //get range for title
        compositeDisposable.add(
                itemInteractor.getPosItemInChannelQueue(this.channelId, this.itemId)
                        .flatMap(absolutePosition -> itemInteractor.getItemsWithOffsetByChannel(this.channelId, absolutePosition - this.pageSize, (this.pageSize * 2) + 1)
                                .doOnSuccess(items -> this.currentAbsPosition = absolutePosition)
                                .flatMap(items -> itemInteractor.getCountItemsByChannel(this.channelId).doOnSuccess(count -> totalItemsCount = count).map(integer -> items)))
                        .toObservable()
                        .concatMapIterable(items -> items)
                        .flatMapMaybe(item -> fileInteractor.getLinkByFileId(item.getEnclosure()).map(imgPath -> {
                            ItemModel itemModel = ItemModel.transform(item);
                            itemModel.setEnclosure(imgPath);
                            return itemModel;
                        }))
                        .subscribe(item -> {
                                    this.itemModels.add(item);
                                }, throwable -> showErrorMessage(new DefaultErrorBundle((Exception) throwable)),
                                () -> {
                                    RequestManager requestManager = Glide.with(mView.context());
                                    viewDetailAdapter = new ViewDetailAdapter(this.itemModels, requestManager, this);
                                    mView.getViewPager().registerOnPageChangeCallback(this);
                                    mView.getViewPager().setAdapter(viewDetailAdapter);

                                    int currentAdapterPosition = 0;
                                    for (ItemModel itemModel : this.itemModels) {
                                        if (itemModel.getItemId().equals(itemId))
                                            currentAdapterPosition = this.itemModels.indexOf(itemModel);
                                    }
                                    this.setTitleForPosition(currentAdapterPosition);
                                    adapterTmpPosition = currentAdapterPosition;
                                    mView.getViewPager().setCurrentItem(currentAdapterPosition, false);
                                }
                        )
        );
    }

    private void setTitleForPosition(int position) {
        globalActions.setTitle(this.currentAbsPosition + "/" + this.totalItemsCount + "  " + this.itemModels.get(position).getTitle());
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
        itemId = mView.getItemId();
        channelId = mView.getChannelId();

        if (itemId > 0 && channelId > 0) {
            initRecycler();
        } else {
            showErrorMessage(new DefaultErrorBundle(new ErrorDetailItemException()));
            navController.navigateUp();
        }
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);

        if (position != adapterTmpPosition) {
            if (position > this.adapterTmpPosition)
                this.currentAbsPosition++;
            else
                this.currentAbsPosition--;
            this.adapterTmpPosition = position;
        }
        this.setTitleForPosition(position);
        this.updateReadStatus(position);
        this.itemId = this.itemModels.get(position).getItemId();
    }

    private void updateReadStatus(int position) {
        compositeDisposable.add(itemInteractor.updateItemReadById(this.itemModels.get(position).getItemId(), true).subscribe(
                integer -> {
                },
                throwable -> showErrorMessage(new DefaultErrorBundle((Exception) throwable))
        ));
    }

    @Override
    public void clickStar() {
        int pos = this.adapterTmpPosition;

        Boolean isStar = this.itemModels.get(pos).getStar();
        if (isStar) {
            compositeDisposable.add(itemInteractor.deleteFavByItemBy(this.itemId).subscribe(integer -> {
            }, throwable -> showErrorMessage(new DefaultErrorBundle((Exception) throwable))));
        } else {
            Favorite favorite = new Favorite();
            favorite.setItemId(itemId);
            favorite.setFavId(itemId);
            favorite.setCategoryId(1L);
            compositeDisposable.add(itemInteractor.insertFavorite(favorite).subscribe(integer -> {
            }, throwable -> showErrorMessage(new DefaultErrorBundle((Exception) throwable))));
        }
        this.itemModels.get(pos).setStar(!isStar);
    }
}