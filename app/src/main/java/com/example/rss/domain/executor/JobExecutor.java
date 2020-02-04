package com.example.rss.domain.executor;

import androidx.annotation.NonNull;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;


public class JobExecutor implements IThreadExecutor {

	private final ThreadPoolExecutor threadPoolExecutor;

	@Inject
	public JobExecutor(){
		threadPoolExecutor = new ThreadPoolExecutor(
				3, 5, 10,
				TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(),
				new JobThreadFactory(),
				new JobRejectedExecutionHandler()
		);
	}

	@Override
	public void execute(@NonNull Runnable runnable) {
		threadPoolExecutor.execute(runnable);
	}

	private static class JobThreadFactory implements ThreadFactory {
		private int counter = 0;

		@Override public Thread newThread(@NonNull Runnable runnable) {
			return new Thread(runnable, "android_" + counter++);
		}
	}

	private static class JobRejectedExecutionHandler implements RejectedExecutionHandler{

		@Override
		public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
			if (!threadPoolExecutor.isShutdown()) {
				try {
					threadPoolExecutor.getQueue().put(runnable);
				} catch (InterruptedException e) {

				}
			}
		}
	}
}
