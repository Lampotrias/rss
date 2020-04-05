package com.example.rss;

import android.app.Application;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.example.rss.presentation.di.component.AppComponent;
import com.example.rss.presentation.di.component.DaggerAppComponent;
import com.example.rss.presentation.di.component.FragmentComponent;
import com.example.rss.presentation.di.component.WorkManagerComponent;
import com.example.rss.presentation.di.module.AppModule;
import com.example.rss.presentation.di.module.FragmentModule;
import com.example.rss.presentation.di.module.WorkManagerModule;


public class AndroidApplication extends Application {
	private AppComponent appComponent;

	private GlobalActivity globalActivity;
	private FragmentComponent fragmentComponent;
	private WorkManagerComponent workManagerComponent;


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
//		if (BuildConfig.DEBUG) {
//
//		}
	}

	public GlobalActivity getGlobalActivity() {
		return globalActivity;
	}

	public void setGlobalActivity(GlobalActivity globalActivity) {
		this.globalActivity = globalActivity;
	}

	public FragmentComponent getFragmentModule(Fragment fragment){
		fragmentComponent = appComponent.plusFragmentComponent(new FragmentModule(fragment));
		return fragmentComponent;
	}

	public void releaseWorkManagerModule(){
		workManagerComponent = null;
	}

	public WorkManagerComponent getWorkManagerModule(){
		workManagerComponent = appComponent.plusWorkManagerComponent(new WorkManagerModule());
		return workManagerComponent;
	}

	public void releaseFragmentModule(){
		fragmentComponent = null;
	}

	public void releaseGlobalActivity(){
		globalActivity = null;
		appComponent = null;
	}


}