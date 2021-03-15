package org.ivcode.stonks.indicator.indicators;

import java.util.List;

import org.ivcode.stonks.indicator.Indicator;
import org.ivcode.stonks.indicator.IndicatorKey;
import org.ivcode.stonks.indicator.Interval;
import org.ivcode.stonks.indicator.IntervalField;
import org.ivcode.stonks.indicator.IntervalManager;
import org.ivcode.stonks.utils.math.LeastSquaresRegression;
import org.ivcode.stonks.utils.math.LinearEquation;
import org.ivcode.stonks.utils.math.PolynomialEquation;
import org.ivcode.stonks.utils.math.PolynomialEquationHelper;

public class LinearRegressionSlope implements Indicator<Double> {
	
	private final String name;
	private final IntervalField field;
	
	public LinearRegressionSlope(String name, IntervalField field) {
		this.name = name;
		this.field = field;
	}

	@Override
	public Double onTick(Interval interval, IntervalManager manager) {
		List<Interval> intervals = manager.getIntervals();
		
		LeastSquaresRegression leastSquareRegression = new LeastSquaresRegression();
		
		int size = intervals.size();
		
		if(size<2) {
			return 0.0;
		}
		
		
		for(int i=0; i<size; i++) {
			Interval in = intervals.get(i);
			leastSquareRegression.with(i, field.getValue(in));
		}
		
		PolynomialEquation eq = leastSquareRegression.getEquation(1);
		LinearEquation linearEq = PolynomialEquationHelper.toLinearEquation(eq);
		return linearEq.getSlope();
	}

	@Override
	public IndicatorKey<Double, LinearRegressionSlope> getKey() {
		return new IndicatorKey<>(LinearRegressionSlope.class, name);
	}
}
