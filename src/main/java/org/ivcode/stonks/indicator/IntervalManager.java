package org.ivcode.stonks.indicator;

import static org.ivcode.stonks.utils.SaftyUtils.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.ivcode.stonks.market.ChartTick;
import org.ivcode.stonks.market.Tick;
import org.ivcode.stonks.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntervalManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IntervalManager.class);
	
	private final TemporalAmount intervalDuration;
	
	private MutableInterval workingInterval;
	private LocalDateTime intervalExpire;
	
	private final int lookbacksMaxSize;
	private final LinkedList<Interval> lookbacks = new LinkedList<>();
	
	private final Collection<Indicator<?>> indicators;
	
	/**
	 * 
	 * @param period
	 * 		the duration of a given period
	 * @param periodUnit
	 * @param periods
	 */
	public IntervalManager(LookbackContext lookback, Collection<Indicator<?>> indicators, Collection<ChartTick> history) {
		this.intervalDuration = toTemporalAmount(lookback.getLookbackDuration(), lookback.getLookbackUnit());
		this.lookbacksMaxSize = lookback.getLookback(); 
		this.indicators = safe(indicators);
		
		safe(history).forEach(this::onTick);
	}
	
	public IntervalManager(LookbackContext lookback, Collection<Indicator<?>> indicators) {
		this(lookback, indicators, null);
	}
	
	public IntervalManager(LookbackContext lookback) {
		this(lookback, null, null); 
	}
	
	public IntervalManager onTick(Tick tick) {
		onTick(tick.getTime(), tick.getPrice(), tick.getPrice(), tick.getPrice(), tick.getPrice(), tick.getVolume());
		return this;
	}
	
	private void onTick(ChartTick tick) {
		onTick(TimeUtils.toLocalDateTime(tick.getDate()), safe(tick.getHigh()), safe(tick.getLow()), safe(tick.getOpen()), safe(tick.getClose()), safe(tick.getVolume()));
	}
	
	private synchronized void onTick(LocalDateTime dateTime, double high, double low, double open, double close, long volume) {
		if(intervalExpire==null) {
			intervalExpire = dateTime.plus(this.intervalDuration);
		}
		
		if(workingInterval !=null && dateTime.isAfter(intervalExpire)) {
			// transfer to lookback
			lookbacks.addLast(new Interval(workingInterval));
			if(lookbacks.size() > lookbacksMaxSize) {
				lookbacks.pop();
			}
			
			while(dateTime.isAfter(intervalExpire)) {
				intervalExpire = intervalExpire.plus(intervalDuration);
			}
			workingInterval = null;
		}
		
		if(workingInterval==null) {
			workingInterval = new MutableInterval();
			workingInterval.setStart(dateTime);
			workingInterval.setOpen(open);
		}
		
		workingInterval.setClose(close);
		workingInterval.setEnd(dateTime);
		
		update(workingInterval, high, low, volume);
		
		runIndicators();
	}
	
	private void runIndicators() {
		for(Indicator<?> i : indicators) {
			
			try {
				Object value = i.onTick(getWorkingInterval(), this);
				if(value!=null) {
					workingInterval.setValue(i.getKey(), value);
				}
			} catch (RuntimeException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
	
	public synchronized List<Interval> getIntervals() {
		return getIntervals(true);
	}
	
	/**
	 * 
	 * @param isIncludeWorking if {@code true}, the returned value will include the
	 *                         working interval. Otherwise, the returned value will
	 *                         not include the working interval
	 * @return The intervals
	 */
	public synchronized List<Interval> getIntervals(boolean isIncludeWorking) {
		List<Interval> intervals = new ArrayList<>(lookbacks);
		
		if(isIncludeWorking) {
			intervals.add(getWorkingInterval());
		}
		
		return intervals;
	}
	
	public Interval getWorkingInterval() {
		return new Interval(workingInterval);
	}
	
	public int getMaxSize() {
		return lookbacksMaxSize + (workingInterval==null ? 0 : 1);
	}
	
	public int getSize() {
		return this.lookbacks.size() + (workingInterval==null ? 0 : 1);
	}
	
	public <T> T getValue(Class<? extends Indicator<T>> type) {
		return getValue(0, new IndicatorKey<>(type, null));
	}
	
	public <T> T getValue(int lookback, Class<? extends Indicator<T>> type) {
		return getValue(lookback, new IndicatorKey<>(type, null));
	}
	
	public <T, I extends Indicator<T>> T getValue(IndicatorKey<T, I> indicator) {
		return getValue(0, indicator);
	}
	
	public <T, I extends Indicator<T>> T getValue(int lookback, IndicatorKey<T, I> indicator) {
		Interval i = getInterval(lookback);
		return i==null ? null : i.getValue(indicator);
	}
	
	public Interval getInterval(int lookback) {
		if(lookback==0) {
			return new Interval(workingInterval);
		}
		
		int lookbackIndex = this.lookbacks.size() - lookback;
		if(lookbackIndex<0) {
			return null;
		}
		
		return lookbacks.get(lookbackIndex);
	}

	private void update(MutableInterval context, double high, double low, long volume) {
		Double oldLow = context.getLow();
		if(oldLow==null || low<oldLow) {
			context.setLow(low);
		}
		
		Double oldHigh = context.getHigh();
		if(oldHigh==null || high>oldHigh) {
			context.setHigh(high);
		}
		
		Long oldVolume = context.getVolume();
		context.setVolume(safe(oldVolume) + volume);
	}
	
	private TemporalAmount toTemporalAmount(long period, TimeUnit periodUnit) {
		TemporalUnit unit = toTemporalUnit(periodUnit);
		return Duration.of(period, unit);
	}
	
	private TemporalUnit toTemporalUnit(TimeUnit unit) {
		switch(unit) {
		case DAYS:
			return ChronoUnit.DAYS;
		case HOURS:
			return ChronoUnit.HOURS;
		case MICROSECONDS:
			return ChronoUnit.MICROS;
		case MILLISECONDS:
			return ChronoUnit.MILLIS;
		case MINUTES:
			return ChronoUnit.MINUTES;
		case NANOSECONDS:
			return ChronoUnit.NANOS;
		case SECONDS:
			return ChronoUnit.SECONDS;
		default:
			throw new IllegalArgumentException("Unknown Time Unit: " + unit);
		}
	}
}
