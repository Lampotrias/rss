package com.example.rss.domain.executor;

import io.reactivex.Scheduler;

public interface IPostExecutionThread {
	Scheduler getScheduler();
}
