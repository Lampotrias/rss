package com.example.rss.presentation.itemDetail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

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
public class ItemDetailFragment extends BaseFragment implements ItemDetailContract.V {
	private AndroidApplication app;

	@Inject	public ItemDetailPresenter mPresenter;
	@Inject	public GlobalActions globalActions;

	@BindView(R.id.vPager)
	ViewPager2 vPager;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		android.view.View rootView = inflater.inflate(R.layout.item_detail_fragment, container, false);
		ButterKnife.bind(this, rootView);

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
}
