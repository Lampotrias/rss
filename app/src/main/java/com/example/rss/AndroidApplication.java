package com.example.rss;

import android.app.Application;
import android.content.Context;


import com.example.rss.presentation.di.component.AppComponent;
import com.example.rss.presentation.di.component.DaggerAppComponent;
import com.example.rss.presentation.di.module.AppModule;
import com.example.rss.presentation.global.GlobalActions;


public class AndroidApplication extends Application {
	private AppComponent appComponent;

	GlobalActivity globalActivity;
	
	@Override public void onCreate() {
		super.onCreate();
		this.initializeInjector();
		this.initializeLeakDetection();
	}

	private void initializeInjector() {
		appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
	}

	public static AndroidApplication get(Context context) {
		return (AndroidApplication) context.getApplicationContext();
	}

	public AppComponent getAppComponent() {
		return appComponent;
	}

	private void initializeLeakDetection() {
		if (BuildConfig.DEBUG) {

		}
	}

	public GlobalActivity getGlobalActivity() {
		return globalActivity;
	}

	public void setGlobalActivity(GlobalActivity globalActivity) {
		this.globalActivity = globalActivity;
	}
}