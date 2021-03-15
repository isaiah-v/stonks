package org.ivcode.stonks.indicator;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents a period of time.
 * 
 * @author isaiah
 */
public class Interval {

	private final double open;
	private final double high;
	private final double low;
	private final double close;
	private final long volume;
	private final LocalDateTime end;
	private final LocalDateTime start;

	private final Map<IndicatorKey<?,?>, Object> values;

	Interval(MutableInterval interval) {
		this.open = interval.getOpen();
		this.high = interval.getHigh();
		this.low = interval.getLow();
		this.close = interval.getClose();
		this.volume = interval.getVolume();
		this.values = Collections.unmodifiableMap(interval.getValues());
		this.end = interval.getEnd();
		this.start = interval.getStart();
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public double getOpen() {
		return open;
	}

	public double getHigh() {
		return high;
	}

	public double getLow() {
		return low;
	}

	public double getClose() {
		return close;
	}

	public long getVolume() {
		return volume;
	}

	@SuppressWarnings("unchecked")
	public <T, I extends Indicator<T>> T getValue(Class<I> type) {
		return (T) values.get(new IndicatorKey<T, I>(type, null));
	}
	
	@SuppressWarnings("unchecked")
	public <T, I extends Indicator<T>> T getValue(Class<I> type, String name) {
		return (T) values.get(new IndicatorKey<T, I>(type, name));
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getValue(IndicatorKey<T, ?> key) {
		return (T) values.get(key);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
