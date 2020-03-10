package com.example.rss.presentation.channelList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rss.databinding.ChannelListFragmentBinding;
import com.example.rss.presentation.BaseFragment;

public class ChannelListFragment extends BaseFragment implements ChannelListContract.V {

    private ChannelListFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ChannelListFragmentBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public Context context() {
        return null;
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
