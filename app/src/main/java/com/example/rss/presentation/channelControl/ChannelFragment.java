package com.example.rss.presentation.channelControl;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rss.AndroidApplication;
import com.example.rss.R;
import com.example.rss.presentation.BaseFragment;
import com.example.rss.presentation.di.module.FragmentModule;
import com.example.rss.presentation.di.scope.ChannelScope;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@ChannelScope
public class ChannelFragment extends BaseFragment implements ChannelContract.V {

	private static ChannelFragment instance = null;

	@Inject
	public ChannelPresenter mPresenter;

	@BindView(R.id.btnAddChannel)
	MaterialButton btnAdd;

	@BindView(R.id.url_text_input)
	TextInputLayout urlTextInput;

	@BindView(R.id.url_text_edit)
	TextInputEditText urlEditText;

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

	@Override
	public void onResume() {
		super.onResume();
		mPresenter.resume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mPresenter.destroy();
	}

	@Nullable
	@Override
	public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		android.view.View rootView = inflater.inflate(R.layout.channel_edit_fragment, container, false);
		ButterKnife.bind(this, rootView);
		btnAdd.setOnClickListener((view)-> onSaveButtonClicked());
		urlEditText.setOnKeyListener((v, keyCode, event) -> {
			String url = String.valueOf(urlEditText.getText());
			if (url.startsWith("http")){
				urlTextInput.setError(null);
				return true;
			}
			return false;
		});
		return rootView;
	}


	private void onSaveButtonClicked() {
		String url = String.valueOf(urlEditText.getText());
		if(url.length()> 0 && url.startsWith("http")){
			urlTextInput.setError(null);
			mPresenter.onSaveButtonClicked(url);
		}else{
			urlTextInput.setError("Введите url");
		}
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

}
