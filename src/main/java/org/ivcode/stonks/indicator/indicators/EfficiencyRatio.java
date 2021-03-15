package org.ivcode.stonks.indicator.indicators;

import org.ivcode.stonks.indicator.Indicator;
import org.ivcode.stonks.indicator.IndicatorKey;
import org.ivcode.stonks.indicator.Interval;
import org.ivcode.stonks.indicator.IntervalField;
import org.ivcode.stonks.indicator.IntervalManager;

public class EfficiencyRatio implements Indicator<Double> {

	private final String name;
	private final IntervalField field;

	public EfficiencyRatio(String name, IntervalField field) {
		this.name = name;
		this.field = field;
	}

	@Override
	public Double onTick(Interval interval, IntervalManager manager) {
		// ER = Change / Volatility

		if (manager.getSize()<=1) {
			return getDefaultValue(manager);
		}

		// Change = ABS (Close – Close[n])
		double change = Math.abs(field.getValue(interval) - field.getValue(manager.getInterval(manager.getSize()-1)));

		// Volatility = n ∑ (ABS(Close – Close[1]))
		double volatility = 0;
		for(int i=0; i<manager.getSize()-1; i++) {
			Interval i0 = manager.getInterval(i);
			Interval i1 = manager.getInterval(i+1);
			
			volatility += Math.abs(field.getValue(i0) - field.getValue(i1));
		}
		
		if(volatility==0.0) {
			return getDefaultValue(manager);
		}

		return change / volatility;
	}
	
	private double getDefaultValue(IntervalManager manager) {
		Double value = manager.getValue(this.getKey());
		return value==null ? 1.0 : value;
	}

	@Override
	public IndicatorKey<Double, EfficiencyRatio> getKey() {
		return new IndicatorKey<>(EfficiencyRatio.class, name);
	}
}
