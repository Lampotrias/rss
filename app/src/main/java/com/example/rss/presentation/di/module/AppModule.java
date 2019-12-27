package com.example.rss.presentation.di.module;

import android.content.Context;

import com.example.rss.AndroidApplication;
import com.example.rss.data.repository.AppDataRepository;
import com.example.rss.domain.executor.IPostExecutionThread;
import com.example.rss.domain.executor.IThreadExecutor;
import com.example.rss.domain.executor.JobExecutor;
import com.example.rss.domain.repositories.IRepository;
import com.example.rss.presentation.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

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
}
