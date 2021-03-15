package org.ivcode.stonks.market.simulator.event;

import java.time.LocalDateTime;

import org.ivcode.stonks.market.Tick;

public class TickEvent extends Event {

	private final Tick tick;

	public TickEvent(LocalDateTime time, Tick tick) {
		super(time);
		this.tick = tick;
	}

	public Tick getTick() {
		return tick;
	}
}
