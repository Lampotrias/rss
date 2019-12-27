package com.example.rss.presentation.channelControl;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rss.AndroidApplication;
import com.example.rss.R;
import com.example.rss.presentation.BaseFragment;
import com.example.rss.presentation.di.module.FragmentModule;
import com.example.rss.presentation.di.scope.ChannelScope;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

@ChannelScope
public class ChannelFragment extends BaseFragment implements ChannelContract.View {

	private static ChannelFragment instance = null;

	@Inject
	public ChannelPresenter mPresenter;

	@BindView(R.id.btnAdd)
	Button btnAdd;

	@BindView(R.id.url)
	EditText url;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplication app = (AndroidApplication) getActivity().getApplication();
		app.getAppComponent().plusFragmentComponent(new FragmentModule(this)).inject(this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mPresenter.setView(this);
		Toast.makeText(getActivity(), "1312312321", Toast.LENGTH_SHORT).show();
	}

	@Nullable
	@Override
	public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		android.view.View rootView = inflater.inflate(R.layout.channel_fragment, container, false);
		ButterKnife.bind(this, rootView);
		btnAdd.setOnClickListener((view)-> onSaveButtonClicked());
		return rootView;
	}

	@Override
	public void onSaveButtonClicked() {
		mPresenter.addNewChannel(url.getText().toString());
	}

	public static ChannelFragment getInstance(){
		if (instance == null) {
			instance = new ChannelFragment();
		}
		return instance;
	}

	@Override
	public void displayError(Throwable throwable) {
		showToastMessage("Error:" + throwable.getMessage());
	}

	@Override
	public void displaySuccess(String message) {
		showToastMessage("Success:" + message);
	}

	@Override
	public Context getContextActivity() {
		return this.getActivity().getApplicationContext();
	}
}
