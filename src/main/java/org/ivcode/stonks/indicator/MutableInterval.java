package org.ivcode.stonks.indicator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;

class MutableInterval {

	private final String id;
	private Double open;
	private Double high;
	private Double low;
	private Double close;
	private Long volume;
	private LocalDateTime end;
	private LocalDateTime start;
	
	private Map<IndicatorKey<?,?>, Object> values = new HashMap<>();

	public MutableInterval() {
		this(UUID.randomUUID().toString());
	}
	
	public MutableInterval(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getClose() {
		return close;
	}

	public void setClose(Double close) {
		this.close = close;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public Long getVolume() {
		return volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
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
	
	public <T> void setValue(IndicatorKey<?, ?> key,  T value) {
		this.values.put(key, value);
	}
	
	public Map<IndicatorKey<?,?>, Object> getValues() {
		return values;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
