package com.example.rss.presentation.itemDetail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.example.rss.AndroidApplication;
import com.example.rss.R;
import com.example.rss.databinding.ItemDetailFragmentBinding;
import com.example.rss.presentation.BaseFragment;
import com.example.rss.presentation.di.scope.ChannelScope;
import com.example.rss.presentation.global.GlobalActions;

import java.util.Objects;

import javax.inject.Inject;

@ChannelScope
public class ItemDetailFragment extends BaseFragment implements ItemDetailContract.V {
    private AndroidApplication app;
    private Long itemId = 0L;
    private Long channelId = 0L;

    public static final String DETAIL_ITEM_ID = "DETAIL_POSITION";
    public static final String DETAIL_CHANNEL_ID = "DETAIL_CHANNEL_ID";

    @Inject
    public ItemDetailPresenter mPresenter;
    @Inject
    public GlobalActions globalActions;

    private ItemDetailFragmentBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemId = getArguments().getLong(DETAIL_ITEM_ID, 0);
            channelId = getArguments().getLong(DETAIL_CHANNEL_ID, 0);
        }
        app = (AndroidApplication) Objects.requireNonNull(getActivity()).getApplication();
        app.getFragmentModule(this).inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.setView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ItemDetailFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void onDestroy() {
        mPresenter.destroy();
        //app.releaseFragmentModule();
        super.onDestroy();
    }

    @Override
    public Context context() {
        return this.getActivity();
    }

    @Override
    public void displayError(String error) {
        showToastMessage(error);
    }

    @Override
    public ViewPager2 getViewPager() {
        return binding.vPager;
    }

    @Override
    public Long getItemId() {
        return itemId;
    }

    @Override
    public Long getChannelId() {
        return channelId;
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
