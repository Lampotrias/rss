package com.example.rss.presentation;

import com.example.rss.presentation.fragment.Presenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class BasePresenter implements Presenter {

	private CompositeDisposable disposables = new CompositeDisposable();

	public void start() {
	}

	public void stop() {
		disposables.clear();
	}

	protected void addDisposable(Disposable disposable) {
		disposables.add(disposable);
	}

	@Override
	public void init() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void destroy() {

	}
}
