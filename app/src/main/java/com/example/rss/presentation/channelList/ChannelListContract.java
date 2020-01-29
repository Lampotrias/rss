package com.example.rss.presentation.channelList;

import android.content.Context;

import com.example.rss.presentation.Presenter;

public interface ChannelListContract {
    interface V {
        Context context();
    }
    interface P<T> extends Presenter {

    }
}
