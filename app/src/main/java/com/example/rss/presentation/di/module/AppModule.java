package com.example.rss.presentation.di.module;

import android.content.Context;

import com.example.rss.AndroidApplication;
import com.example.rss.data.repository.AppDataRepository;
import com.example.rss.data.repository.datasource.impl.CacheApp;
import com.example.rss.data.repository.datasource.impl.ICacheApp;
import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;
import com.example.rss.domain.executor.JobExecutor;
import com.example.rss.domain.repositories.IRepository;
import com.example.rss.presentation.UIThread;
import com.example.rss.presentation.global.GlobalActions;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final AndroidApplication application;

    public AppModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides @Singleton
    IThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides @Singleton
    IPostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides @Singleton
    IRepository provideAppDataRepository(AppDataRepository userDataRepository) {
        return userDataRepository;
    }

    @Provides @Singleton
    ICacheApp provideCacheDataStore (CacheApp cacheApp){
        return cacheApp;
    }

    @Provides @Singleton
    GlobalActions provideGlobalActions(){
        return application.getGlobalActivity();
    }
}
