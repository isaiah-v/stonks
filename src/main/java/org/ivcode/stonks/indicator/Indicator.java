package org.ivcode.stonks.indicator;

/**
 * A stock indicator are measuring tools to aid in predicting where the price is headed
 * @author isaiah
 *
 * @param <T>
 */
public interface Indicator<T> {
	
	/**
	 * Called when a market tick occurs
	 * @param interval
	 * 		The current interval
	 * @param manager
	 * 		The manager handling the intervals
	 * @return
	 * 		The indicator's result
	 */
	T onTick(Interval interval, IntervalManager manager);
	
	IndicatorKey<T, ? extends Indicator<T>> getKey();
}
