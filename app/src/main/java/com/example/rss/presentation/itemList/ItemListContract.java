package com.example.rss.presentation.itemList;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rss.presentation.Presenter;

public interface ItemListContract {
	interface V {
		Context context();
		void displayError(String error);
		void stopRefresh();
		RecyclerView getRecycler();
		int getResourceIdRowView();
		Long getCurChannelId();
	}

	interface P<T> extends Presenter {
		void setView(T view);
		void refreshList();
	}
}
