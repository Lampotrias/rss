package com.example.rss.presentation.channelControl;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.rss.R;
import com.example.rss.presentation.BaseActivity;


public class ChannelActivity extends BaseActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channel_activity);
		this.initializeActivity(savedInstanceState);
	}

	@Override
	protected void setupActivityComponent() {

	}

	private void initializeActivity(Bundle savedInstanceState) {
		if(savedInstanceState == null){
			addFragment(R.id.channel_fragment, ChannelFragment.getInstance());
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void finish() {
		super.finish();
	}
}
