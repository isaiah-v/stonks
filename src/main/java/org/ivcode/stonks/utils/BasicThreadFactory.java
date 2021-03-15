package org.ivcode.stonks.utils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class BasicThreadFactory implements ThreadFactory {

	private final String name;
	private final boolean isDaemon;
	private final AtomicInteger index = new AtomicInteger(1);

	public BasicThreadFactory(String name, boolean isDaemon) {
		this.name = name;
		this.isDaemon = isDaemon;
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread th = new Thread(r);
		th.setName(name + '-' + index.getAndIncrement());
		th.setDaemon(isDaemon);
		
		return th;
	}

}
