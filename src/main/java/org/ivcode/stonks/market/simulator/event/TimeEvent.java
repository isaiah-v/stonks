package org.ivcode.stonks.market.simulator.event;

import java.time.LocalDateTime;

import org.ivcode.stonks.market.MarketStatus;

public class TimeEvent extends Event {

	private final MarketStatus status;

	public TimeEvent(LocalDateTime time, MarketStatus status) {
		super(time);
		this.status = status;
	}

	public MarketStatus getStatus() {
		return status;
	}
}
