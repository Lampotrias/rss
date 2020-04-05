package com.example.rss.presentation.categoryList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;

import com.example.rss.presentation.Presenter;

public interface CategoryListContract {
    interface V {
        Context context();
        ViewGroup getRootView();
        LayoutInflater getInflater();
        int getRowResourceId();
        FragmentManager getManager();
    }
    interface P extends Presenter {
        void setView(V view);
        void addCategoryMenu();
    }
}
