package com.example.rss.presentation.channelEdit;

import android.content.Context;

import com.example.rss.presentation.Presenter;

public interface ChannelEditContract {
	interface V {
		void displayError (String throwable);
		void displaySuccess(String message);
		Context context();
		void isEnable(Boolean bDisable);
	}

	interface P<T> extends Presenter {
		void onSaveButtonClicked(String url, Boolean bCacheImage, Boolean bDownloadFull, Boolean bOnlyWifi);
		void onCancelButtonClicked();
		void setView(T view);
	}
}
