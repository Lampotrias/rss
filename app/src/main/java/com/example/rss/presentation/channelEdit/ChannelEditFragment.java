package com.example.rss.presentation.channelEdit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rss.AndroidApplication;
import com.example.rss.R;
import com.example.rss.domain.Channel;
import com.example.rss.presentation.BaseFragment;
import com.example.rss.presentation.di.scope.ChannelScope;
import com.example.rss.presentation.global.GlobalActions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@ChannelScope
public class ChannelEditFragment extends BaseFragment implements ChannelEditContract.V {

	private AndroidApplication app;
    private Long curChannelId = 0L;
    public static final String CHANNEL_ID_PARAM = "CHANNEL_ID_PARAM";

	@Inject
	public ChannelEditPresenter mPresenter;

	@Inject
	public GlobalActions globalActions;

	@BindView(R.id.btnAddChannel)
	MaterialButton btnSave;

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

	private Long categoryId;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        if (getArguments() != null){
            curChannelId = getArguments().getLong(CHANNEL_ID_PARAM, 0);
        }

		app = (AndroidApplication) Objects.requireNonNull(getActivity()).getApplication();
		app.getFragmentModule(this).inject(this);
	}

	@Nullable
	@Override
	public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		android.view.View rootView = inflater.inflate(R.layout.channel_edit_fragment, container, false);
		ButterKnife.bind(this, rootView);
		mPresenter.setView(this);

		btnSave.setOnClickListener((view)-> onSaveButtonClicked());
		urlEditText.setOnKeyListener((v, keyCode, event) -> {
			String url = String.valueOf(urlEditText.getText());
			if (url.startsWith("http")){
				urlTextInput.setError(null);
				return true;
			}
			return false;
		});

		btnCancel.setOnClickListener(v -> mPresenter.onCancelButtonClicked());
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
	public void setSaveButtonEnable(Boolean bDisable) {
		btnSave.setEnabled(bDisable);
	}

    @Override
    public Long getCurChannelId() {
        return curChannelId;
    }

	@Override
	public void drawNewChannelWindow() {
		urlEditText.setText("https://www.feedforall.com/sample.xml");
		btnSave.setText("Добавить");

		ckCacheImage.setChecked(false);
		ckDownloadFullText.setChecked(false);
		ckOnlyWifi.setChecked(false);
	}

	@Override
	public void drawEditChannelWindow(Channel channel) {
		urlEditText.setText(channel.getSourceLink());
		btnSave.setText("Сохранить");

		ckCacheImage.setChecked(channel.getCacheImage());
		ckDownloadFullText.setChecked(channel.getDownloadFullText());
		ckOnlyWifi.setChecked(channel.getOnlyWifi());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		mPresenter.resume();
	}

	@Override
	public void onDestroy() {
		mPresenter.destroy();
		app.releaseFragmentModule();
		super.onDestroy();
	}
}
