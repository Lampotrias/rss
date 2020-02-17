package com.example.rss.presentation.itemDetail;

import android.util.Log;

import androidx.navigation.NavController;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.rss.domain.Item;
import com.example.rss.domain.exception.DefaultErrorBundle;
import com.example.rss.domain.exception.IErrorBundle;
import com.example.rss.presentation.exception.ErrorDetailItemException;
import com.example.rss.presentation.exception.ErrorMessageFactory;
import com.example.rss.presentation.global.GlobalActions;
import com.example.rss.presentation.itemDetail.adapter.ViewDetailAdapter;
import com.example.rss.presentation.itemDetail.adapter.ViewDetailPresenter;
import com.example.rss.presentation.itemList.adapter.ItemModel;
import com.example.rss.presentation.itemList.adapter.RecyclerListAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class ItemDetailPresenter extends ViewPager2.OnPageChangeCallback implements ItemDetailContract.P<ItemDetailContract.V> {
	private ItemDetailContract.V mView;
	private final ItemDetailInteractor ItemDetailInteractor;
	private final CompositeDisposable compositeDisposable;
	private List<ItemModel> itemModels;
	private int needShowItemId;
	@Inject
	GlobalActions globalActions;

	@Inject
	NavController navController;

	@Inject
	ItemDetailPresenter(ItemDetailInteractor itemListInteractor) {
		this.ItemDetailInteractor = itemListInteractor;
		compositeDisposable = new CompositeDisposable();
	}

	void initRecycler(){
		itemModels = new ArrayList<>();
		compositeDisposable.add(ItemDetailInteractor.getItemsByChannelId(mView.getChannelId())
				.toObservable()
				.concatMapIterable(items -> items)
				.subscribe(item -> {
							ItemModel itemModel = transform(item);
							compositeDisposable.add(
									ItemDetailInteractor.getFileById(item.getEnclosure())
											.subscribe(file -> itemModel.setEnclosure(file.getPath()), throwable -> Log.e("myApp", "error")));
							itemModels.add(itemModel);

						}, throwable -> showErrorMessage(new DefaultErrorBundle((Exception) throwable)),
						() -> {
							RequestManager requestManager = Glide.with(mView.context());
							ViewDetailPresenter viewDetailPresenter = new ViewDetailPresenter(requestManager, itemModels, mView.getResourceIdRowView());
							ViewDetailAdapter viewDetailAdapter = new ViewDetailAdapter(viewDetailPresenter);
							mView.getViewPager().registerOnPageChangeCallback(this);
							mView.getViewPager().setAdapter(viewDetailAdapter);
							mView.getViewPager().setCurrentItem(needShowItemId, false);


						})
		);

	}

	private ItemModel transform(Item item){
		DateFormat format = new SimpleDateFormat("dd-MMMM-yyyy HH:mm", Locale.getDefault());

		ItemModel model = new ItemModel();
		model.setItemId(item.getItemId());
		model.setGuid(item.getGuid());
		model.setTitle(item.getTitle());
		model.setDescription(item.getDescription());
		model.setLink(item.getLink());
		model.setPubDate(format.format(item.getPubDate() * 1000));
		model.setRead(item.getRead());
		model.setStar(item.getFavorite());
		return model;
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
		needShowItemId = mView.getItemId();

		if (needShowItemId == -1){
			showErrorMessage(new DefaultErrorBundle(new ErrorDetailItemException()));
			navController.navigateUp();
		}else{
			initRecycler();
		}
	}

	@Override
	public void onPageSelected(int position) {
		super.onPageSelected(position);
		//String builder
		globalActions.setTitle(position+1 + "/" + itemModels.size() + "  " + itemModels.get(position).getTitle());
	}
}
