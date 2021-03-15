package org.ivcode.stonks.indicator.indicators;

import java.util.List;

import org.ivcode.stonks.indicator.Indicator;
import org.ivcode.stonks.indicator.IndicatorKey;
import org.ivcode.stonks.indicator.Interval;
import org.ivcode.stonks.indicator.IntervalField;
import org.ivcode.stonks.indicator.IntervalManager;

public class SimpleMovingAverage implements Indicator<Double> {
	
	private final String name;
	private final IntervalField field;
	
	public SimpleMovingAverage(String name, IntervalField field) {
		this.name = name;
		this.field = field;
	}

	@Override
	public Double onTick(Interval interval, IntervalManager manager) {
		List<Interval> intervals = manager.getIntervals();
		
		double total = 0;
		
		for(Interval i : intervals) {
			total += field.getValue(i);
		}
		
		return total / intervals.size();
	}

	@Override
	public IndicatorKey<Double, SimpleMovingAverage> getKey() {
		return new IndicatorKey<>(SimpleMovingAverage.class, name);
	}
}
