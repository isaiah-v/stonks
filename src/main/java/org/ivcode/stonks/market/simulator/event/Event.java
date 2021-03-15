package org.ivcode.stonks.market.simulator.event;

import java.time.LocalDateTime;

public abstract class Event implements Comparable<Event> {
	
	private final LocalDateTime time;
	
	public Event(LocalDateTime time) {
		this.time = time;
	}
	
	public LocalDateTime getTime() {
		return time;
	}
	
	@Override
	public int compareTo(Event event) {
		return this.time.compareTo(event.time);
	}
}
