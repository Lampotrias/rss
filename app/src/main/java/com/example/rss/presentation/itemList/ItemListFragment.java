package com.example.rss.presentation.itemList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.rss.AndroidApplication;
import com.example.rss.R;
import com.example.rss.presentation.BaseFragment;
import com.example.rss.presentation.di.scope.ChannelScope;
import com.example.rss.presentation.global.GlobalActions;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@ChannelScope
public class ItemListFragment extends BaseFragment implements ItemListContract.V {
	private AndroidApplication app;
	private Long curChannelId = 0L;

	@Inject	public ItemListPresenter mPresenter;
	@Inject	public GlobalActions globalActions;

	@BindView(R.id.swipe_refresh_layout)
	SwipeRefreshLayout refreshLayout;

	@BindView(R.id.item_list_recycler)
	RecyclerView itemListRecycler;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null){
			curChannelId = getArguments().getLong("channelId", 0);
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
	public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		android.view.View rootView = inflater.inflate(R.layout.items_list_fragment, container, false);
		ButterKnife.bind(this, rootView);

		refreshLayout.setOnRefreshListener(() -> mPresenter.refreshList());

		refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		return rootView;
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
		refreshLayout.setRefreshing(false);
	}

	@Override
	public RecyclerView getRecycler() {
		return itemListRecycler;
	}

	@Override
	public int getResourceIdRowView() {
		return R.layout.card_list_item_row;
	}

	@Override
	public Long getCurChannelId() {
		return curChannelId;
	}
}
