package org.ivcode.stonks.market.simulator.event;

public interface EventListener {
	void onTimeEvent(TimeEvent event);
	void onTickEvent(TickEvent event);
}
