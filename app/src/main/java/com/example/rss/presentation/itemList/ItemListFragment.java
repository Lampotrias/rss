package com.example.rss.presentation.itemList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rss.AndroidApplication;

import com.example.rss.databinding.ItemsListFragmentBinding;
import com.example.rss.presentation.BaseFragment;
import com.example.rss.presentation.di.scope.ChannelScope;
import com.example.rss.presentation.global.GlobalActions;

import java.util.Objects;

import javax.inject.Inject;

@ChannelScope
public class ItemListFragment extends BaseFragment implements ItemListContract.V {
    private AndroidApplication app;
    private Long curChannelId = 0L;
    private boolean bFavMode = false;

    public static final String CHANNEL_ID = "CHANNEL_ID";
    public final static String FAVORITES_MODE = "FAVORITES_MODE";

    @Inject
    public ItemListPresenter mPresenter;
    @Inject
    public GlobalActions globalActions;

    private ItemsListFragmentBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            curChannelId = getArguments().getLong(CHANNEL_ID, 0);
            bFavMode = getArguments().getBoolean(FAVORITES_MODE, false);
        }
        app = (AndroidApplication) Objects.requireNonNull(getActivity()).getApplication();
        app.getFragmentModule(this).inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ItemsListFragmentBinding.inflate(inflater, container, false);

        binding.swipeRefreshLayout.setOnRefreshListener(() -> mPresenter.refreshList());

        binding.swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

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
    public void stopRefresh() {
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public RecyclerView getRecycler() {
        return binding.itemListRecycler;
    }

    @Override
    public Long getCurChannelId() {
        return curChannelId;
    }

    @Override
    public boolean isFavoriteMode() {
        return bFavMode;
    }

    @Override
    public void setEmptyView(Boolean isShow) {
        if (isShow) {
            binding.emptyView.setVisibility(View.VISIBLE);
            binding.itemListRecycler.setVisibility(View.GONE);
        } else {
            binding.emptyView.setVisibility(View.GONE);
            binding.itemListRecycler.setVisibility(View.VISIBLE);
        }
    }
}
