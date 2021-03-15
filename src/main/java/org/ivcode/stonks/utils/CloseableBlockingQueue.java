package org.ivcode.stonks.utils;

import java.io.Closeable;
import java.util.Deque;
import java.util.LinkedList;

public class CloseableBlockingQueue<T> implements Closeable {
	
	private boolean isOpen = true;
	private final Deque<T> deque = new LinkedList<>();
	
	public T next() throws InterruptedException {
		synchronized (deque) {
			while(isOpen && deque.isEmpty()) {
				deque.wait();
			}
			
			if(!isOpen && deque.isEmpty()) {
				return null;
			} else {
				return deque.pollFirst();
			}
		}
	}
	
	public int size() {
		return deque.size();
	}
	
	public boolean isEmpty() {
		return deque.isEmpty();
	}
	
	public void add(T t) {
		if(t==null) {
			return;
		}
		
		synchronized (deque) {
			if(!isOpen) {
				throw new IllegalStateException("closed");
			}
			
			deque.add(t);
			deque.notify();
		}
	}
	
	@Override
	public void close() {
		synchronized (deque) {
			if(!isOpen) {
				return;
			}
			
			isOpen = false;
			deque.notifyAll();
		}
	}}
