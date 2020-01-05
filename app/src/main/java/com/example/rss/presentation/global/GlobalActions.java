package com.example.rss.presentation.global;

import androidx.fragment.app.Fragment;

public interface GlobalActions {
    void setTitle(String title);
    void replaceFragment(Fragment fragment);
}
