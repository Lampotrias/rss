package com.example.rss.presentation.di.module;

import androidx.fragment.app.Fragment;

import dagger.Module;

@Module
public class FragmentModule {

    Fragment mFragment;

    public FragmentModule(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    public Fragment provideFragment() {
        return mFragment;
    }

}
