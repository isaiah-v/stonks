package org.ivcode.stonks.invester.ratings;

import java.util.List;

import org.ivcode.stonks.graph.GraphBuilder;
import org.ivcode.stonks.graph.GraphBuilderNop;
import org.ivcode.stonks.indicator.Interval;
import org.ivcode.stonks.indicator.IntervalManager;
import org.ivcode.stonks.indicator.LookbackContext;
import org.ivcode.stonks.indicator.indicators.VariableMovingAverage;
import org.ivcode.stonks.market.ChartTick;
import org.ivcode.stonks.market.Tick;
import org.ivcode.stonks.utils.Factory;
import org.ivcode.stonks.utils.math.LinearEquation;
import org.ivcode.stonks.utils.math.Point;

public class VariableMovingAverageRating implements RatingIndicator {

	private final String name;
	private final double bound;
	private final LookbackContext lookback;

	private final GraphBuilder graph;

	private IntervalManager intervals;

	public VariableMovingAverageRating(String name, double bound, LookbackContext lookback, GraphBuilder graph) {
		this.name = name;
		this.bound = bound;
		this.lookback = lookback;

		this.graph = graph;
	}

	@Override
	public double getRating(Tick tick) {
		Double avg = intervals.onTick(tick).getValue(VariableMovingAverage.class);

		double upperBound = getUpperBound();
		double lowserBound = getLowerBound();

		double upperSpread = bound * upperBound;
		double lowserSpread = bound * lowserBound;

		double min = avg - (avg * lowserSpread);
		double max = avg + (avg * upperSpread);

		graph.withPoint(name, avg);
		graph.withPoint(name + "-top", max);
		graph.withPoint(name + "-bottom", min);

		LinearEquation eq = LinearEquation.createLinearEquation(new Point(min, 1), new Point(max, -1));

		double price = tick.getPrice();

		double rating = eq.calculate(price);
		graph.withLineNote(name, "RATING", Double.toString(rating));

		return rating;
	}

	@Override
	public void init(List<ChartTick> history) {
		intervals = VariableMovingAverage.createVmaIntervalManager(lookback, history);
	}

	public static Factory<VariableMovingAverageRating> createRateableFactory(double bound, LookbackContext lookback) {
		return () -> new VariableMovingAverageRating("VMA", bound, lookback, GraphBuilderNop.INSTANCE);
	}

	private double getUpperBound() {
		double upperBound = 0.0;
		for (Interval i : intervals.getIntervals(false)) {
			double avg = i.getValue(VariableMovingAverage.class);
			double price = i.getClose();

			if (avg > price) {
				continue;
			}

			double bound = Math.abs(price - avg) / price;
			if (bound > upperBound) {
				upperBound = bound;
			}
		}

		return upperBound;
	}

	private double getLowerBound() {
		double lowerBound = 0.0;

		for (Interval i : intervals.getIntervals(false)) {
			double avg = i.getValue(VariableMovingAverage.class);
			double price = i.getClose();

			if (avg < price) {
				continue;
			}

			double bound = Math.abs(price - avg) / price;
			if (bound > lowerBound) {
				lowerBound = bound;
			}
		}

		return lowerBound;
	}

	@Override
	public <T, R> R visit(RatingIndicatorVisitor<T, R> visitor, T argument) {
		return visitor.apply(this, argument);
	}

	public String getName() {
		return name;
	}

	public double getBound() {
		return bound;
	}

	public LookbackContext getLookback() {
		return lookback;
	}

}
