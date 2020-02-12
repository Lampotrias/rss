package com.example.rss.presentation.itemDetail;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.rss.presentation.Presenter;

public interface ItemDetailContract {
	interface V {
		Context context();
		void displayError(String error);
		int getResourceIdRowView();
		ViewPager2 getViewPager();
		int getItemId();
	}

	interface P<T> extends Presenter {
		void setView(T view);
	}
}
