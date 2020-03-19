package com.example.rss.presentation.global;

import android.content.Context;

import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.rss.presentation.services.SyncService;

public class InitBackgroundWork {

    private final Context context;

    public InitBackgroundWork(Context context) {
        this.context = context;
    }

    public void createAllTasks() {
        startRefreshWork();
    }

    private void startRefreshWork() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresDeviceIdle(true)
                .setRequiresCharging(true)
                .build();

        OneTimeWorkRequest refreshWork = new OneTimeWorkRequest.Builder(SyncService.class)
                // .setConstraints(constraints)
                .build();

        WorkManager.getInstance(context).enqueue(refreshWork);
    }
}
