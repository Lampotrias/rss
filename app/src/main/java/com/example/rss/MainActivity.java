package com.example.rss;

import android.content.Intent;

import android.os.Bundle;

import com.example.rss.presentation.BaseActivity;
import com.example.rss.presentation.channelControl.ChannelActivity;


public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = new Intent(this, ChannelActivity.class);
		startActivity(intent);
	}

	@Override
	protected void setupActivityComponent() {

	}
}
