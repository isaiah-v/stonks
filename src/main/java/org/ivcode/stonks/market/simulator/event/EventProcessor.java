package org.ivcode.stonks.market.simulator.event;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventProcessor implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventProcessor.class);

	private final List<Event> events;
	private final Set<EventListener> listeners;

	public EventProcessor(List<Event> events, Set<EventListener> listeners) {
		this.events = events;
		this.listeners = listeners;
	}

	@Override
	public void run() {
		for (Event event : events) {
			if (TickEvent.class.isAssignableFrom(event.getClass())) {
				triggerTick((TickEvent) event);
			} else if (TimeEvent.class.isAssignableFrom(event.getClass())) {
				triggerTime((TimeEvent) event);
			} else {
				throw new IllegalStateException("unknown event type: " + event.getClass());
			}
		}
	}

	private void triggerTick(TickEvent event) {
		for (EventListener listener : listeners) {
			try {
				listener.onTickEvent(event);
			} catch (RuntimeException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	private void triggerTime(TimeEvent event) {
		for (EventListener listener : listeners) {
			try {
				listener.onTimeEvent(event);
			} catch (RuntimeException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
}
