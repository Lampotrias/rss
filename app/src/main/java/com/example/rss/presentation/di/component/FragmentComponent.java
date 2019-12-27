package com.example.rss.presentation.di.component;

import com.example.rss.presentation.channelControl.ChannelFragment;
import com.example.rss.presentation.di.module.FragmentModule;
import com.example.rss.presentation.di.scope.ChannelScope;

import dagger.Subcomponent;

@ChannelScope
@Subcomponent (modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(ChannelFragment channelFragment);
}
