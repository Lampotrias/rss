package com.example.rss.presentation.channelControl;

import com.example.rss.presentation.Presenter;

public interface ChannelContract {
	interface V {
		void onSaveButtonClicked();
		void displayError (Throwable throwable);
		void displaySuccess(String message);
	}

	interface P<T> extends Presenter {
		void setView(T view);
		void ShowExitsChannel(int id);
		void addNewChannel(String url);
	}
}
