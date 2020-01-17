package com.example.rss.presentation.channelControl;

import com.example.rss.presentation.Presenter;

public interface ChannelContract {
	interface V {
		void displayError (Throwable throwable);
		void displaySuccess(String message);
	}

	interface P<T> extends Presenter {
		void onSaveButtonClicked(String url, Boolean bCacheImage, Boolean bDownloadFull, Boolean bOnlyWifi);
		void onCancelButtonClicked();
		void setView(T view);
		void addNewChannel(String url);
	}
}
