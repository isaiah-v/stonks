package org.ivcode.stonks.indicator.indicators;

import static org.ivcode.stonks.indicator.IntervalManagerHelper.*;

import org.ivcode.stonks.indicator.Indicator;
import org.ivcode.stonks.indicator.IndicatorKey;
import org.ivcode.stonks.indicator.Interval;
import org.ivcode.stonks.indicator.IntervalManager;

public class WilliamsPercentRange implements Indicator<Double> {

	private final String name;

	public WilliamsPercentRange(String name) {
		this.name = name;
	}

	@Override
	public Double onTick(Interval interval, IntervalManager manager) {
		// Williams %R = (HighestHigh - Close) / (HighestHigh - LowestLow)

		Interval i = getSummary(manager);
		return 1 - (i.getHigh() - i.getClose()) / (i.getHigh() - i.getLow());
	}

	@Override
	public IndicatorKey<Double, WilliamsPercentRange> getKey() {
		return new IndicatorKey<>(WilliamsPercentRange.class, name);
	}
}
