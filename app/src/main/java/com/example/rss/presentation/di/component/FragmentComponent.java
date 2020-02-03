package com.example.rss.presentation.di.component;

import com.example.rss.presentation.channelEdit.ChannelEditFragment;
import com.example.rss.presentation.di.module.FragmentModule;
import com.example.rss.presentation.di.scope.ChannelScope;

import dagger.Subcomponent;

@ChannelScope
@Subcomponent (modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(ChannelEditFragment channelEditFragment);
}
