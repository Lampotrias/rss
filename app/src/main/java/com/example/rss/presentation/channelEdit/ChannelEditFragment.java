package com.example.rss.presentation.channelEdit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rss.AndroidApplication;
import com.example.rss.databinding.ChannelEditFragmentBinding;
import com.example.rss.domain.Channel;
import com.example.rss.presentation.BaseFragment;
import com.example.rss.presentation.di.scope.ChannelScope;
import com.example.rss.presentation.global.GlobalActions;

import java.util.Objects;

import javax.inject.Inject;

@ChannelScope
public class ChannelEditFragment extends BaseFragment implements ChannelEditContract.V {

    private AndroidApplication app;
    private Long curChannelId = 0L;
    public static final String CHANNEL_ID_PARAM = "CHANNEL_ID_PARAM";

    private ChannelEditFragmentBinding binding;

    @Inject
    public ChannelEditPresenter mPresenter;

    @Inject
    public GlobalActions globalActions;

    private Long categoryId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            curChannelId = getArguments().getLong(CHANNEL_ID_PARAM, 0);
        }

        app = (AndroidApplication) Objects.requireNonNull(getActivity()).getApplication();
        app.getFragmentModule(this).inject(this);
    }

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ChannelEditFragmentBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        mPresenter.setView(this);

        binding.btnSaveChannel.setOnClickListener(v -> onSaveButtonClicked());

        binding.urlTextEdit.setOnKeyListener((v, keyCode, event) -> {
            String url = String.valueOf(binding.urlTextEdit.getText());
            if (url.startsWith("http")) {
                binding.urlTextInput.setError(null);
                return true;
            }
            return false;
        });

        binding.btnCancel.setOnClickListener(v -> mPresenter.onCancelButtonClicked());
        return rootView;
    }

    private void onSaveButtonClicked() {
        String url = String.valueOf(binding.urlTextEdit.getText());
        if (url.length() > 0 && url.startsWith("http")) {
            binding.urlTextInput.setError(null);
            mPresenter.onSaveButtonClicked(url, binding.ckCacheImage.isChecked(), binding.ckDownloadFullText.isChecked(), binding.ckOnlyWifi.isChecked());
        } else {
            binding.urlTextInput.setError("Введите url");
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
        binding.btnSaveChannel.setEnabled(bDisable);
    }

    @Override
    public Long getCurChannelId() {
        return curChannelId;
    }

    @Override
    public void drawNewChannelWindow() {
        binding.urlTextEdit.setText("https://www.feedforall.com/sample.xml");
        binding.btnSaveChannel.setText("Добавить");

        binding.ckCacheImage.setChecked(false);
        binding.ckDownloadFullText.setChecked(false);
        binding.ckOnlyWifi.setChecked(false);
    }

    @Override
    public void drawEditChannelWindow(Channel channel) {
        binding.urlTextEdit.setText(channel.getSourceLink());
        binding.btnSaveChannel.setText("Сохранить");

        binding.ckCacheImage.setChecked(channel.getCacheImage());
        binding.ckDownloadFullText.setChecked(channel.getDownloadFullText());
        binding.ckOnlyWifi.setChecked(channel.getOnlyWifi());
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
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        mPresenter.destroy();
        app.releaseFragmentModule();
        super.onDestroy();
    }
}
