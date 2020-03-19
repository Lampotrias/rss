package com.example.rss.presentation.services;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.testing.TestListenableWorkerBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Single;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class SyncServiceTest {

    private Context mContext;

    @Before
    public void setUp() throws Exception {
        mContext = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void createWork() {
        Data inputData = new Data.Builder()
                .putLong("SLEEP_DURATION", 10000L)
                .build();

        TestListenableWorkerBuilder<SyncService> worker = TestListenableWorkerBuilder.from(mContext, SyncService.class);
        worker.setInputData(inputData);
        Single<ListenableWorker.Result> result = worker.build().createWork();

        assertThat(result, is(ListenableWorker.Result.success()));
    }
}