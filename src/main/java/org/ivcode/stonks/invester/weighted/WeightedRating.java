package org.ivcode.stonks.invester.weighted;

import java.util.List;

import org.ivcode.stonks.invester.ratings.RatingIndicator;
import org.ivcode.stonks.market.ChartTick;
import org.ivcode.stonks.market.Tick;

public class WeightedRating {

	private final RatingIndicator ratingIndicator;
	private final double weight;

	public WeightedRating(RatingIndicator ratingIndicator, double weight) {
		this.ratingIndicator = ratingIndicator;
		this.weight = weight;
	}

	/**
	 * The weight to be applied to this investlet. This a value between 0 and 1
	 * where 0 implies no weight and 1 implies full weight
	 * 
	 * @return The weight for this investlet
	 */
	public double getWeight() {
		return weight;
	}

	public RatingIndicator getRatingIndicator() {
		return ratingIndicator;
	}

	public double getRating(Tick tick) {
		return ratingIndicator.getRating(tick);
	}

	public void init(List<ChartTick> history) {
		ratingIndicator.init(history);
	}
}
