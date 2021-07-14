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
 */
public class ProfilerQueue{
	
	private final static Logger log = LoggerFactory.getLogger(ProfilerQueue.class);

	/**
	 * 线程安全队列 工作队列
	 */
	private final BlockingQueue<ProfileInfo> queue;
	/**
	 * 线程工厂，用以创建线程
	 */
	private final ThreadFactory threadFactory;
	/**
	 * 线程，接收线程工厂创建的线程
	 */
	private Thread thread;
	/**
	 * Boolean型原子变量，确保线程安全
	 */
	private final AtomicBoolean started = new AtomicBoolean(false);
	/**
	 * 线程安全的boolean变量
	 * 表示是否继续
	 */
	private volatile boolean shouldContinue = false;

	/**
	 * NoArgsConstructor
	 */
	public ProfilerQueue() {
		this(null);
	}

	public ProfilerQueue(final ThreadFactory tf) {
		this.queue = new LinkedBlockingQueue<>();
		this.threadFactory = tf == null ? Executors.defaultThreadFactory() : tf;
		this.thread = null;
	}

	/**
	 * 开启监控方法，写入EzProfilerController
	 */
	public void start() {
		if (started.getAndSet(true)) {
			return;
		}
		log.info("WorkingQueue start");
		shouldContinue = true;
		// 创建一个线程实现 addProfilerInfo 的方法
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

	/**
	 * 停止监控方法，未写入Controller层
	 */
	public void stop() {
		started.set(false);
		shouldContinue = false;
		thread.interrupt();
		log.info("WorkingQueue end");
	}

	/**
	 * 将Profiler数据入队列，
	 * @param info 单次调用统计数据类
	 */
	public void addProfileInfo(ProfileInfo info) {
		if (!started.get()) {
			start();
		}
		queue.add(info);
	}
}