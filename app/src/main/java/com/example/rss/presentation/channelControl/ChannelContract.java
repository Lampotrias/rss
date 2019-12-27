package com.example.rss.presentation.channelControl;

import android.content.Context;

public interface ChannelContract {
	interface View {
		//void setPresenter(ChannelContract.Presenter presenter);
		void onSaveButtonClicked();
		void displayError (Throwable throwable);
		void displaySuccess(String message);
		Context getContextActivity();
	}

	interface Presenter {
		void setView(ChannelContract.View view);
		void ShowExitsChannel(int id);
		void addNewChannel(String url);
	}
}
