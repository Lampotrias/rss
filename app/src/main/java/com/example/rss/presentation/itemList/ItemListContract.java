package com.example.rss.presentation.itemList;

import android.content.Context;

import com.example.rss.presentation.Presenter;

public interface ItemListContract {
	interface V {
		Context context();
	}

	interface P<T> extends Presenter {
		void setView(T view);
	}
}
