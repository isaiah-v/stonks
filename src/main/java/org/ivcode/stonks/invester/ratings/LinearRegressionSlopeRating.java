package org.ivcode.stonks.invester.ratings;

import static java.util.Arrays.*;

import java.util.List;

import org.ivcode.stonks.graph.GraphBuilder;
import org.ivcode.stonks.graph.GraphBuilderNop;
import org.ivcode.stonks.indicator.IntervalField;
import org.ivcode.stonks.indicator.IntervalManager;
import org.ivcode.stonks.indicator.LookbackContext;
import org.ivcode.stonks.indicator.indicators.LinearRegressionSlope;
import org.ivcode.stonks.market.ChartTick;
import org.ivcode.stonks.market.Tick;
import org.ivcode.stonks.utils.Factory;
import org.ivcode.stonks.utils.math.Equation;
import org.ivcode.stonks.utils.math.LeastSquaresRegression;

public class LinearRegressionSlopeRating implements RatingIndicator {

	private final String name;
	private final double lower;
	private final double upper;
	private final LookbackContext lookback;

	private final GraphBuilder graph;

	private final Equation eq;
	private IntervalManager intervalManager;

	public LinearRegressionSlopeRating(String name, double lower, double upper, LookbackContext lookback, GraphBuilder graph) {
		this.name = name;
		this.lower = lower;
		this.upper = upper;
		this.lookback = lookback;

		this.graph = graph;

		eq = new LeastSquaresRegression()
				.with(upper, 0)
				.with(lower, 0)
				.with((upper+lower)/2, 1)
				.getEquation(2);
	}

	@Override
	public double getRating(Tick tick) {
		intervalManager.onTick(tick);

		double value = intervalManager.getValue(LinearRegressionSlope.class);
		graph.withPoint(name, value);

		double rating = Math.max(eq.calculate(value), 0);
		graph.withLineNote(name, "RATING", Double.toString(rating));

		return rating;
	}

	@Override
	public void init(List<ChartTick> history) {
		LinearRegressionSlope i = new LinearRegressionSlope(null, IntervalField.CLOSE);
		this.intervalManager = new IntervalManager(lookback, asList(i), history);
	}

	@Override
	public <T, R> R visit(RatingIndicatorVisitor<T, R> visitor, T argument) {
		throw new UnsupportedOperationException();
	}

	public String getName() {
		return name;
	}

	public double getLower() {
		return lower;
	}

	public double getUpper() {
		return upper;
	}

	public LookbackContext getLookback() {
		return lookback;
	}

	public static Factory<LinearRegressionSlopeRating> createRateableFactory(double lower, double upper, LookbackContext lookback) {
		return () -> new LinearRegressionSlopeRating("LR Slope", lower, upper, lookback, GraphBuilderNop.INSTANCE);
	}

}
