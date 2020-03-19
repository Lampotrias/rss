package com.example.rss.presentation.di.component;

import com.example.rss.presentation.channelEdit.ChannelEditFragment;
import com.example.rss.presentation.di.module.FragmentModule;
import com.example.rss.presentation.di.scope.ChannelScope;
import com.example.rss.presentation.itemDetail.ItemDetailFragment;
import com.example.rss.presentation.itemList.ItemListFragment;

import dagger.Subcomponent;

@ChannelScope
@Subcomponent (modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(ChannelEditFragment channelEditFragment);
    void inject(ItemListFragment itemListFragment);
    void inject(ItemDetailFragment itemDetailFragment);
}
