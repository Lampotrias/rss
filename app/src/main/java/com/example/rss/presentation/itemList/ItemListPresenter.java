package com.example.rss.presentation.itemList;

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
		List<ItemModel> itemModels= new ArrayList<>();
		compositeDisposable.add(itemListInteractor.getItemsByChannelId(1L)
				.toFlowable()
				.concatMapIterable(items -> items)
				.subscribe(item -> {
					Log.e("myApp", item.getItemId() + "==1subscribe_item = " + item.getTitle());
					ItemModel itemModel = transform(item);
					compositeDisposable.add(
							itemListInteractor.getFileById(item.getEnclosure())
							.subscribe(file -> {
								Log.e("myApp", "2subscribe_file = " + item.getTitle() + " === " + file.getPath());
								itemModel.setEnclosure(file.getPath());
							},throwable -> Log.e("myApp", "error")));
					Log.e("myApp", "3add_itemModel = " + itemModel.getTitle());
					itemModels.add(itemModel);

				}, throwable -> showErrorMessage(new DefaultErrorBundle((Exception) throwable)),
						() -> {
							Log.e("myApp", "4itemModels = " + itemModels.size());
							RequestManager requestManager = Glide.with(mView.context());
							RepositoriesListPresenter repositoriesListPresenter = new RepositoriesListPresenter(requestManager, itemModels);
							RepositoriesRecyclerAdapter recyclerAdapter = new RepositoriesRecyclerAdapter(repositoriesListPresenter);
							RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mView.context());
							mView.getRecycler().setLayoutManager(layoutManager);
							mView.getRecycler().setAdapter(recyclerAdapter);
				})
		);

	}

	private void showErrorMessage(IErrorBundle errorBundle) {
		String errorMessage = ErrorMessageFactory.create(this.mView.context(), errorBundle.getException());
		mView.displayError(errorMessage);
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
	public void setView(ItemListContract.V view) {
		this.mView = view;
		initRecycler();
	}

	@Override
	public void refreshList() {
//		List<Item> items = new ArrayList<>();
//		Item item;
//			item = new Item();
//			//item.setItemId(1L);
//			item.setChannelId(1L);
//			item.setTitle("Лукашенко намекнул на приезд Трампа из-за обмана России по газу");
//			item.setDescription("Во время посещения Добрушской бумажной фабрики глава государства рассказал, что 7 февраля намерен встретиться со своим российским коллегой Владимиром Путиным и объяснить ему, что «наступил некий момент истины». Лукашенко напомнил, что в ходе покупки «Белтрансгаза» Москва обещала за пять лет вывести цены на газ для республики на внутрироссийский уровень. Вместо этого российская сторона сделала вид, что ничего не было, и забыла про обещания.\n" +
//					"\n" +
//					"«Мы хотим просто честных, чистых, прозрачных отношений. Не хотите — пусть скажут нам об этом. И не кричат: \"Ах, Помпео приехал. Завтра Трамп приедет... Что они будут делать?\"», — возмутился Лукашенко. Он подчеркнул, что Белоруссия имеет право проводить независимую внешнюю политику, и это не навредит России.");
//			item.setEnclosure(1L);
//			item.setGuid("21234234123");
//			item.setLink("http://sdsdfdsfd/dsfsdfsfd.php");
//			item.setPubDate("1580830952");
//			item.setRead(true);
//
//		Item item2;
//		item2 = new Item();
//		//item2.setItemId(1L);
//		item2.setChannelId(1L);
//		item2.setTitle("Стало известно о новых санкциях США против «Северного потока-2»");
//		item2.setDescription("США намерены ввести новые санкции против «Северного потока-2». Об этом сообщает немецкое издание Handelsblatt со ссылкой на источники в дипломатических кругах. Это произойдет, если Россия попробует достроить газопровод.\n" +
//				"\n" +
//				"Предполагается, что под санкции могут попасть европейские инвесторы, участвующие в проекте, а также те фирмы, которые захотят покупать газ, если он пойдет по трубопроводу. Газета указывает, что новые санкции могут быть введены в феврале или марте.");
//		item2.setEnclosure(2L);
//		item2.setGuid("21234274123");
//		item2.setLink("http://sdsdfdsfd/dsfsdfsfd.php");
//		item2.setPubDate("1580820951");
//		item2.setRead(false);
//
//		items.add(item);
//		items.add(item2);
//
//		compositeDisposable.add(
//				itemListInteractor.InsertItems(items)
//				.subscribe(() -> {Toast.makeText(mView.context(), "Ok",Toast.LENGTH_SHORT).show();})
//		);

		mView.stopRefresh();
	}
}
