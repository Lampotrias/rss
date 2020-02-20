package com.example.rss.presentation.itemDetail;


import com.example.rss.domain.File;
import com.example.rss.domain.Item;
import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;
import com.example.rss.domain.repositories.IRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

class ItemDetailInteractor {

	private final IThreadExecutor threadExecutor;
	private final IPostExecutionThread postExecutionThread;
	private final IRepository channelRepository;

	@Inject
	public ItemDetailInteractor(IRepository channelRepository, IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread){
		this.threadExecutor = threadExecutor;
		this.postExecutionThread = postExecutionThread;
		this.channelRepository = channelRepository;
	}

	/*public Observable<List<Item>> getItemsByChannelId(Long id) {
		return Observable.create(emitter -> {
			Item item = new Item();
			item.setItemId(1L);
			item.setTitle("Раскрыто состояние зараженного коронавирусом гражданина Китая в России");
			item.setDescription("Вирус 2019-nCoV передается от человека к человеку. К основным симптомам инфекции относят повышенную температуру, утомляемость и кашель с небольшой мокротой. Помимо КНР, заболевание бы");
			item.setEnclosure(1L);
			item.setGuid("");
			item.setChannelId(1L);
			item.setLink("ff");
			item.setPubDate(12111123L);
			item.setRead(true);
			item.setFavorite(false);
			Item item2 = new Item();
			item2.setItemId(2L);
			item2.setTitle("title2");
			item2.setDescription("des2");
			item2.setEnclosure(2L);
			item2.setGuid("");
			item2.setChannelId(1L);
			item2.setLink("gg");
			item2.setPubDate(12131123L);
			item2.setRead(false);
			item2.setFavorite(true);

			List<Item> arr = new ArrayList<>();
			arr.add(item);
			arr.add(item2);
			emitter.onNext(arr);
			emitter.onComplete();
		});
	}*/

	Maybe<List<Item>> getItemsByChannelId (Long id) {
		return channelRepository.getItemsByChannelId(id)
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());
	}

	Maybe<File> getFileById (Long id) {
		return channelRepository.getFileById(id)
				.subscribeOn(Schedulers.from(threadExecutor))
				.observeOn(postExecutionThread.getScheduler());
	}
}
