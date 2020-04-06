package com.example.rss.presentation.channelEdit;

import android.content.Context;

import com.example.rss.domain.Category;
import com.example.rss.domain.Channel;
import com.example.rss.presentation.Presenter;

import java.util.List;

public interface ChannelEditContract {
	interface V {
		void displayError (String throwable);
		void displaySuccess(String message);
		Context context();
		void setSaveButtonEnable(Boolean bDisable);
        Long getCurChannelId();
        void drawNewChannelWindow();
        void drawEditChannelWindow(Channel channel);
        void fillingCategories(List<Category> strings);
	}

	interface P<T> extends Presenter {
		void onSaveButtonClicked(String url, Long categoryId, Boolean bCacheImage, Boolean bDownloadFull, Boolean bOnlyWifi);
		void onCancelButtonClicked();
		void setView(T view);
	}
}
