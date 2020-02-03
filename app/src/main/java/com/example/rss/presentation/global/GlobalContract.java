package com.example.rss.presentation.global;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.example.rss.presentation.Presenter;

import java.util.List;
import java.util.Map;

public interface GlobalContract {
    interface V{
        void showMessage(String message);
        void openDrawer();
        void closeDrawer();
        int getNavHostViewId();
        void onClickMenuSettings();
        Context context();
        void displayError (String errorMessage);
        void ShowChannelListMenu(List<Map<String, String>> groupData, List<List<Map<String, String>>> childData, String attrParentTitle, String attrChildTitle);
    }

    interface P<T> extends Presenter {
        void setView(T view);
        void OnClickChannelAdd(View view);
        void openSettingsFragment();
    }
}
