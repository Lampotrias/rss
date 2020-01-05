package com.example.rss.presentation.global;

import android.view.View;

import com.example.rss.presentation.Presenter;

public interface GlobalContract {
    interface V{
        void showMessage(String message);
        void openDrawer();
        void closeDrawer();

    }

    interface P<T> extends Presenter {
        void setView(T view);
        void OnClickChannelAdd(View view);
    }
}
