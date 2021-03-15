package org.ivcode.stonks.invester.ratings;

import static java.util.Arrays.asList;

import java.util.List;

import org.ivcode.stonks.graph.GraphBuilder;
import org.ivcode.stonks.graph.GraphBuilderNop;
import org.ivcode.stonks.indicator.IntervalManager;
import org.ivcode.stonks.indicator.LookbackContext;
import org.ivcode.stonks.indicator.indicators.WilliamsPercentRange;
import org.ivcode.stonks.market.ChartTick;
import org.ivcode.stonks.market.Tick;
import org.ivcode.stonks.utils.Factory;
import org.ivcode.stonks.utils.math.Equation;
import org.ivcode.stonks.utils.math.LinearEquation;
import org.ivcode.stonks.utils.math.Point;

public class WilliamsPercentRangeRating implements RatingIndicator {

	private final String name;
	private final double overbought;
	private final double oversold;
	private final LookbackContext lookback;

	private final GraphBuilder graph;

	private final Equation eq;
	private IntervalManager intervals;

	public WilliamsPercentRangeRating(String name, double oversold, double overbought, LookbackContext lookback,
			GraphBuilder graph) {
		this.name = name;
		this.overbought = overbought;
		this.oversold = oversold;
		this.lookback = lookback;

		this.graph = graph;

		this.eq = LinearEquation.createLinearEquation(new Point(overbought, -1), new Point(oversold, 1));
	}

	@Override
	public double getRating(Tick tick) {
		intervals.onTick(tick);

		double value = intervals.getValue(WilliamsPercentRange.class);
		graph.withPoint(name, value);

		double rating = eq.calculate(value);
		graph.withLineNote(name, "RATING", Double.toString(rating));

		return rating;
	}

	@Override
	public void init(List<ChartTick> history) {
		WilliamsPercentRange williams = new WilliamsPercentRange(null);
		intervals = new IntervalManager(lookback, asList(williams), history);
	}

	@Override
	public <T, R> R visit(RatingIndicatorVisitor<T, R> visitor, T argument) {
		return visitor.apply(this, argument);
	}

	public String getName() {
		return name;
	}

	public double getOverbought() {
		return overbought;
	}

	public double getOversold() {
		return oversold;
	}

	public LookbackContext getLookback() {
		return lookback;
	}

	public static Factory<WilliamsPercentRangeRating> createRateableFactory(double overbought, double oversold, LookbackContext lookback) {
		return () -> new WilliamsPercentRangeRating("Williams %R", overbought, oversold, lookback, GraphBuilderNop.INSTANCE);
	}
}
