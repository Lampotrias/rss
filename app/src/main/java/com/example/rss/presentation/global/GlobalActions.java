package com.example.rss.presentation.global;

import android.content.Context;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public interface GlobalActions {
    Context getActivityContext();
    void setTitle(String title);
    void updDrawerMenu();
    FloatingActionButton getFab();
}
