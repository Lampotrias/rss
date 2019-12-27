package com.example.rss.domain.interactor;

import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class UseCase<Results, Params> {
	private final IThreadExecutor IThreadExecutor;
	private final IPostExecutionThread IPostExecutionThread;
	private final CompositeDisposable disposables;

	UseCase(IThreadExecutor IThreadExecutor, IPostExecutionThread IPostExecutionThread) {
		this.IThreadExecutor = IThreadExecutor;
		this.IPostExecutionThread = IPostExecutionThread;
		this.disposables = new CompositeDisposable();
	}

	abstract Observable<Results> buildUseCaseObservable(Params params);

	public void execute(DisposableObserver<Results> observer, Params params) {
		final Observable<Results> observable = this.buildUseCaseObservable(params)
				.subscribeOn(Schedulers.from(IThreadExecutor))
				.observeOn(IPostExecutionThread.getScheduler());
		addDisposable(observable.subscribeWith(observer));
	}

	private void addDisposable(Disposable disposable) {
		disposables.add(disposable);
	}

	public void dispose() {
		if (!disposables.isDisposed()) {
			disposables.dispose();
		}
	}
}
