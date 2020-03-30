package com.example.rss.presentation.fab;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FabController {
    private FloatingActionButton actionButton;
    private Action actionCallback;

    public FabController(@NonNull FloatingActionButton actionButton, @NonNull Action actionCallback) {
        this.actionButton = actionButton;
        this.actionCallback = actionCallback;

        initButton();
    }

    public void setVisibility(boolean b){
        if (b) {
            this.actionButton.setVisibility(View.VISIBLE);
        } else {
            this.actionButton.setVisibility(View.GONE);
        }
    }

    public void release(){
        this.actionCallback = null;
        this.actionButton = null;
    }

    private void initButton(){
        actionButton.setOnClickListener(actionCallback::clickOnFab);
        actionButton.setImageResource(actionCallback.setIcon());
    }

    public interface Action{
        int setIcon();
        void clickOnFab(View v);
    }
}
