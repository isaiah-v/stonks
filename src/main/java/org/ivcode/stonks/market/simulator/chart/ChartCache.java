package org.ivcode.stonks.market.simulator.chart;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ivcode.stonks.repository.yahoo.v8.model.ChartResponse;
import org.ivcode.stonks.service.yahoo.v8.model.Interval;

public class ChartCache {
	
	private final Map<ChartKey, ChartResponse> cache = new HashMap<>();
	
	public synchronized ChartResponse get(String symbol, LocalDate startDate, LocalDate endDate, Interval interval) {
		ChartKey key = new ChartKey(symbol, startDate, endDate, interval);
		return cache.get(key);
	}
	
	public synchronized void put(String symbol, LocalDate startDate, LocalDate endDate, Interval interval, ChartResponse value) {
		ChartKey key = new ChartKey(symbol, startDate, endDate, interval);
		cache.put(key, value);
	}
	
	public Map<ChartKey, ChartResponse> getData() {
		return Collections.unmodifiableMap(cache);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public static class ChartKey {
		
		private final String symbol;
		private final LocalDate startDate;
		private final LocalDate endDate;
		private final Interval interval;

		public ChartKey(String symbol, LocalDate startDate, LocalDate endDate, Interval interval) {
			this.symbol = symbol;
			this.startDate = startDate;
			this.endDate = endDate;
			this.interval = interval;
		}

		public String getSymbol() {
			return symbol;
		}

		public LocalDate getStartDate() {
			return startDate;
		}

		public LocalDate getEndDate() {
			return endDate;
		}

		public Interval getInterval() {
			return interval;
		}
		
		@Override
		public boolean equals(Object obj) {
			return EqualsBuilder.reflectionEquals(this, obj);
		}
		
		@Override
		public int hashCode() {
			return HashCodeBuilder.reflectionHashCode(this);
		}
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}
	}
}
