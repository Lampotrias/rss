package com.example.rss.presentation.itemDetail;

import android.content.Context;

import androidx.viewpager2.widget.ViewPager2;

import com.example.rss.presentation.Presenter;

public interface ItemDetailContract {
    interface V {
        Context context();
        void displayError(String error);
        ViewPager2 getViewPager();
        Long getItemId();
        Long getChannelId();
    }

    interface P<T> extends Presenter {
        void setView(T view);
    }
}
