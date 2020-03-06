package com.example.rss.presentation.channelEdit;

import android.content.Context;

import com.example.rss.domain.Channel;
import com.example.rss.presentation.Presenter;

public interface ChannelEditContract {
	interface V {
		void displayError (String throwable);
		void displaySuccess(String message);
		Context context();
		void setSaveButtonEnable(Boolean bDisable);
        Long getCurChannelId();
        void drawNewChannelWindow();
        void drawEditChannelWindow(Channel channel);
	}

	interface P<T> extends Presenter {
		void onSaveButtonClicked(String url, Boolean bCacheImage, Boolean bDownloadFull, Boolean bOnlyWifi);
		void onCancelButtonClicked();
		void setView(T view);
	}
}
