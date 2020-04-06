package com.example.rss.presentation.channelEdit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rss.AndroidApplication;
import com.example.rss.databinding.ChannelEditFragmentBinding;
import com.example.rss.domain.Category;
import com.example.rss.domain.Channel;
import com.example.rss.presentation.BaseFragment;
import com.example.rss.presentation.di.scope.ChannelScope;
import com.example.rss.presentation.global.GlobalActions;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

@ChannelScope
public class ChannelEditFragment extends BaseFragment implements ChannelEditContract.V {

    private AndroidApplication app;
    private Long curChannelId = 0L;
    private List<Category> categories;
    public static final String CHANNEL_ID_PARAM = "CHANNEL_ID_PARAM";

    private ChannelEditFragmentBinding binding;
    private SpinnerAdapter spinnerAdapter;

    @Inject
    public ChannelEditPresenter mPresenter;

    @Inject
    public GlobalActions globalActions;

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
            Category category = (Category)binding.catList.getSelectedItem();
            mPresenter.onSaveButtonClicked(url, category.getCategoryId(), binding.ckCacheImage.isChecked(), binding.ckDownloadFullText.isChecked(), binding.ckOnlyWifi.isChecked());
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

        Long curCategoryId = channel.getCategoryId();
        for (Category category: categories) {
            if (category.getCategoryId().equals(curCategoryId)){
                int pos = spinnerAdapter.getPosition(category);
                binding.catList.setSelection(pos);
            }
        }
    }

    @Override
    public void fillingCategories(List<Category> categories) {
        this.categories = categories;
        spinnerAdapter = new SpinnerAdapter(this.context(), android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.catList.setAdapter(spinnerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void onDestroy() {
        mPresenter.destroy();
        binding = null;
        app.releaseFragmentModule();
        super.onDestroy();
    }

    private static class SpinnerAdapter extends ArrayAdapter<Category> {

        private LayoutInflater mInflater;
        private int plainResource;
        private int mDropDownResource;

        SpinnerAdapter(@NonNull Context context, int plainResource, @NonNull List<Category> objects) {
            super(context, plainResource, objects);
            mInflater = LayoutInflater.from(context);
            this.plainResource = plainResource;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return createView(this.mInflater, position, convertView, parent, this.mDropDownResource);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return createView(this.mInflater, position, convertView, parent, this.plainResource);
        }

        private View createView(LayoutInflater inflater, int position, View convertView, ViewGroup parent, int resource){
            final View view;
            final TextView text;

            if (convertView == null)
                view = mInflater.inflate(this.plainResource, parent, false);
            else
                view = convertView;

            try{
                text = (TextView) view;

            }catch (ClassCastException e) {
                throw new IllegalStateException(
                        "ArrayAdapter requires the resource ID to be a TextView", e);
            }

            final Category item = getItem(position);
            text.setText(item.getName());

            return view;
        }



        @Override
        public void setDropDownViewResource(int resource) {
            this.mDropDownResource = resource;
            super.setDropDownViewResource(resource);
        }
    }
}
