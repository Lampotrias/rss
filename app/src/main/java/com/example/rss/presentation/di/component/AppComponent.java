package com.example.rss.presentation.di.component;

import com.example.rss.AndroidApplication;
import com.example.rss.GlobalActivity;
import com.example.rss.presentation.di.module.AppModule;
import com.example.rss.presentation.di.module.FragmentModule;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

	FragmentComponent plusFragmentComponent (FragmentModule fragmentModule);
	void inject (AndroidApplication androidApplication);
	void inject (GlobalActivity globalActivity);
}
