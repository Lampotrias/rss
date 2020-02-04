package com.example.rss.presentation.channelList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rss.R;
import com.example.rss.presentation.BaseFragment;

import butterknife.ButterKnife;

public class ChannelListFragment extends BaseFragment implements ChannelListContract.V {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.channel_list_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public Context context() {
        return null;
    }
}
