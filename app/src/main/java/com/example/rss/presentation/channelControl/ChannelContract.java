package com.example.rss.presentation.channelControl;

import android.content.ContentValues;
import android.content.Context;

import com.example.rss.presentation.Presenter;

public interface ChannelContract {
	interface V {
		void displayError (String throwable);
		void displaySuccess(String message);
		Context context();
	}

	interface P<T> extends Presenter {
		void onSaveButtonClicked(String url, Boolean bCacheImage, Boolean bDownloadFull, Boolean bOnlyWifi);
		void onCancelButtonClicked();
		void setView(T view);
		void addNewChannel(String url);
	}
}
