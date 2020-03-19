package com.example.rss.presentation.di.component;

import com.example.rss.presentation.di.module.WorkManagerModule;
import com.example.rss.presentation.di.scope.ChannelScope;
import com.example.rss.presentation.services.SyncService;

import dagger.Subcomponent;

@ChannelScope
@Subcomponent(modules = WorkManagerModule.class)
public interface WorkManagerComponent {
    void inject(SyncService service);
}
