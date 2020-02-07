package com.example.rss.presentation.itemDetail;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rss.presentation.Presenter;

public interface ItemDetailContract {
	interface V {
		Context context();
		void displayError(String error);
	}

	interface P<T> extends Presenter {
		void setView(T view);
	}
}
