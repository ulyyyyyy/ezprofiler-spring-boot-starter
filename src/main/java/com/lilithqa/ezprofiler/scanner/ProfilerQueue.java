package com.lilithqa.ezprofiler.scanner;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 黑黑
 * @apiNote 工作队列
 * @date 2021-03-04
 */
public class ProfilerQueue{
	
	private final static Logger log = LoggerFactory.getLogger(ProfilerQueue.class);

	private final BlockingQueue<ProfileInfo> queue;
	private final ThreadFactory threadFactory;
	private Thread thread;
	private final AtomicBoolean started = new AtomicBoolean(false);
	private volatile boolean shouldContinue = false;

	public ProfilerQueue() {
		this(null);
	}

	public ProfilerQueue(final ThreadFactory tf) {
		this.queue = new LinkedBlockingQueue<ProfileInfo>();
		this.threadFactory = tf == null ? Executors.defaultThreadFactory() : tf;
		this.thread = null;
	}

	public void start() {
		if (started.getAndSet(true)) {
			return;
		}
		log.info("WorkingQueue start");
		shouldContinue = true;
		thread = threadFactory.newThread(new Runnable() {
			@Override
			public void run() {
				while (shouldContinue) {
					try {
						ProfileInfo req = queue.take();
						ProfileInfoHolder.addProfilerInfo(req);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}

	public void stop() {
		started.set(false);
		shouldContinue = false;
		thread.interrupt();
		log.info("WorkingQueue end");
	}

	/**
	 * 将Profiler数据入队列，
	 * @param info
	 */
	public void addProfileInfo(ProfileInfo info) {
		if (!started.get()) {
			start();
		}
		queue.add(info);
	}
}