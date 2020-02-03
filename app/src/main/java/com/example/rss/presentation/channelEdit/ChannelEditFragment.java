package com.example.rss.presentation.channelEdit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rss.AndroidApplication;
import com.example.rss.R;
import com.example.rss.presentation.BaseFragment;
import com.example.rss.presentation.di.module.FragmentModule;
import com.example.rss.presentation.di.scope.ChannelScope;
import com.example.rss.presentation.global.GlobalActions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@ChannelScope
public class ChannelEditFragment extends BaseFragment implements ChannelEditContract.V {

	private static ChannelEditFragment instance;
	private AndroidApplication app;

	@Inject
	public ChannelEditPresenter mPresenter;

	@Inject
	public GlobalActions globalActions;

	@BindView(R.id.btnAddChannel)
	MaterialButton btnAdd;

	@BindView(R.id.btnCancel)
	MaterialButton btnCancel;

	@BindView(R.id.url_text_input)
	TextInputLayout urlTextInput;

	@BindView(R.id.url_text_edit)
	TextInputEditText urlEditText;

	@BindView(R.id.ck_cache_image)
	MaterialCheckBox ckCacheImage;

	@BindView(R.id.ck_download_full_text)
	MaterialCheckBox ckDownloadFullText;

	@BindView(R.id.ck_only_wifi)
	MaterialCheckBox ckOnlyWifi;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (AndroidApplication) getActivity().getApplication();
		app.getFragmentModule(this).inject(this);
	}



	@Nullable
	@Override
	public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		android.view.View rootView = inflater.inflate(R.layout.channel_edit_fragment, container, false);
		ButterKnife.bind(this, rootView);

		urlEditText.setText("https://www.feedforall.com/sample.xml");

		btnAdd.setOnClickListener((view)-> onSaveButtonClicked());
		urlEditText.setOnKeyListener((v, keyCode, event) -> {
			String url = String.valueOf(urlEditText.getText());
			if (url.startsWith("http")){
				urlTextInput.setError(null);
				return true;
			}
			return false;
		});

		btnCancel.setOnClickListener(v -> {
			mPresenter.onCancelButtonClicked();
		});
		return rootView;
	}


	private void onSaveButtonClicked() {
		String url = String.valueOf(urlEditText.getText());
		if(url.length()> 0 && url.startsWith("http")){
			urlTextInput.setError(null);

			mPresenter.onSaveButtonClicked(url, ckCacheImage.isChecked(), ckDownloadFullText.isChecked(), ckOnlyWifi.isChecked());
		}else{
			urlTextInput.setError("Введите url");
		}
	}

	public static ChannelEditFragment getInstance(){
		if (instance == null) {
			instance = new ChannelEditFragment();
		}
		return instance;
	}

	@Override
	public void displayError(String errorMessage) {
		showToastMessage(errorMessage);
	}

	@Override
	public void displaySuccess(String message) {
		showToastMessage("Success:" + message);
	}

	@Override
	public Context context() {
		return this.getActivity();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mPresenter.setView(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		mPresenter.resume();
	}

	@Override
	public void onDestroy() {
		instance = null;
		mPresenter.destroy();
		app.releaseFragmentModule();
		super.onDestroy();
	}
}
