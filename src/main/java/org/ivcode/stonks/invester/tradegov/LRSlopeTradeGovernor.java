package org.ivcode.stonks.invester.tradegov;

import java.time.LocalDateTime;
import java.util.List;

import org.ivcode.stonks.graph.GraphBuilder;
import org.ivcode.stonks.indicator.LookbackContext;
import org.ivcode.stonks.invester.ratings.LinearRegressionSlopeRating;
import org.ivcode.stonks.invester.ratings.RatingIndicator;
import org.ivcode.stonks.market.ChartTick;
import org.ivcode.stonks.market.Tick;

/**
 * A trade governor based that determines the number of shares to trade based on
 * the price slope
 * 
 * @author isaiah
 *
 */
public class LRSlopeTradeGovernor implements TradeGovernor {

	private final String name;

	private final int buyTrades;
	private final int sellTrades;

	private final double lower;
	private final double upper;
	private final LookbackContext lookback;

	private final int waitTime;

	private final TradeState buyState;
	private final TradeState sellState;

	private RatingIndicator indicator;

	public LRSlopeTradeGovernor(String name, int buyTrades, int sellTrades, double lower, double upper, LookbackContext lookback,
			int waitTime, GraphBuilder graph) {
		this.name = name;

		this.buyTrades = buyTrades;
		this.sellTrades = sellTrades;

		this.lower = lower;
		this.upper = upper;
		this.lookback = lookback;
		this.waitTime = waitTime;

		this.indicator = new LinearRegressionSlopeRating(name, lower, upper, lookback, graph);

		this.buyState = new TradeState(buyTrades, waitTime);
		this.sellState = new TradeState(sellTrades, waitTime);
	}

	@Override
	public int getQuantity(double qualityRating, Tick tick) {
		double rating = indicator.getRating(tick);

		if (Math.abs(qualityRating) < 1) {
			return 0;
		}

		TradeState tradeState = getTradeState(qualityRating);
		int quantity = (int) (qualityRating * rating * tradeState.trades);
		if (quantity == 0) {
			return 0;
		}

		if (tradeState.isWaiting(tick)) {
			return 0;
		}
		tradeState.update(qualityRating, tick);

		return Math.abs(quantity);
	}

	private TradeState getTradeState(double qualityRating) {
		return qualityRating >= 0 ? buyState : sellState;
	}

	@Override
	public void init(List<ChartTick> history) {
		this.indicator.init(history);
	}

	public String getName() {
		return name;
	}

	public int getBuyTrades() {
		return buyTrades;
	}

	public int getSellTrades() {
		return sellTrades;
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

	public int getWaitTime() {
		return waitTime;
	}

	private static class TradeState {
		private final int trades;
		private final int waitTime;

		private LocalDateTime waitExpired;
		private double qualityRating;

		public TradeState(int trades, int waitTime) {
			this.trades = trades;
			this.waitTime = waitTime;
		}

		public boolean isWaiting(Tick tick) {
			return this.waitExpired == null ? false : this.waitExpired.isAfter(tick.getTime());
		}

		public double getQualityDelta(double qualityRating) {
			return Math.abs(qualityRating) / this.qualityRating;
		}

		public void update(double qualityRating, Tick tick) {
			this.waitExpired = tick.getTime().plusHours(waitTime);
			this.qualityRating = qualityRating;
		}
	}

	@Override
	public <T, R> R visit(TradeGovernorVisitor<T, R> visitor, T argument) {
		return visitor.apply(this, argument);
	}
}