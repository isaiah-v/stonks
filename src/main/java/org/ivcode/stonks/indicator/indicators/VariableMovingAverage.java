package org.ivcode.stonks.indicator.indicators;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.ivcode.stonks.indicator.Indicator;
import org.ivcode.stonks.indicator.IndicatorKey;
import org.ivcode.stonks.indicator.Interval;
import org.ivcode.stonks.indicator.IntervalField;
import org.ivcode.stonks.indicator.IntervalManager;
import org.ivcode.stonks.indicator.LookbackContext;
import org.ivcode.stonks.market.ChartTick;

public class VariableMovingAverage implements Indicator<Double> {
	
	private final String name;
	private final IntervalField field;
	
	private final IndicatorKey<Double, EfficiencyRatio> efficiencyRatioKey;
	
	public VariableMovingAverage (String name, IntervalField field, IndicatorKey<Double, EfficiencyRatio> efficiencyRatioKey) {
		this.name = name;
		this.field = field;
		this.efficiencyRatioKey = efficiencyRatioKey;
	}

	@Override
	public Double onTick(Interval interval, IntervalManager manager) {
		// VMA = (α * VI * Close) + ((1 – ( α * VI )) * VMA[1])
		// α = 2 / (N + 1)
		// VI = Users choice of a measure of volatility or trend strength
		// N = User selected constant smoothing period
		
		double alpha = getAlpha(manager);
		double vi = getVi(manager);
		double value = field.getValue(interval);
		Double vma1 = manager.getValue(1, this.getKey());
		
		double vma = getVMA(alpha, vi, value, vma1==null ? 0 : vma1);
		
		return vma;
	}
	
	private double getVMA(double alpha, double vi, double value, double vma1) {
		return (alpha * vi * value) + ((1 - ( alpha * vi )) * vma1);
	}
	
	private double getAlpha(IntervalManager manager) {
		// α = 2 / (N + 1)
		int n = manager.getMaxSize();;
		
		return 2.0/(n+1.0);
	}
	
	private double getVi(IntervalManager manager) {
		// VI = Users choice of a measure of volatility or trend strength
		// VI is set to Kaufman Efficiency Ratio
		
		Double value = manager.getValue(0, this.efficiencyRatioKey);
		if(value==null) {
			throw new IllegalStateException("Efficiency Ratio is exspected to run berfore VMA");
		}
		
		return value;
	}

	@Override
	public IndicatorKey<Double, VariableMovingAverage> getKey() {
		return new IndicatorKey<>(VariableMovingAverage.class, name);
	}
	
	public static List<Indicator<?>> createVmaIndicators() {
		EfficiencyRatio efficiencyRatio = new EfficiencyRatio(UUID.randomUUID().toString(), IntervalField.CLOSE);
		VariableMovingAverage vma = new VariableMovingAverage(null, IntervalField.CLOSE, efficiencyRatio.getKey());
		
		return Arrays.asList(efficiencyRatio, vma);
	}
	
	public static IntervalManager createVmaIntervalManager(LookbackContext lookback, List<ChartTick> history) {
		return new IntervalManager(lookback, createVmaIndicators(), history);
	}
}
