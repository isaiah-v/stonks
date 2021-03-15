package org.ivcode.stonks.invester.weighted;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.ivcode.stonks.graph.GraphBuilder;
import org.ivcode.stonks.graph.GraphBuilderFactory;
import org.ivcode.stonks.invester.Investor;
import org.ivcode.stonks.invester.InvestorVisitor;
import org.ivcode.stonks.invester.tradegov.TradeGovernor;
import org.ivcode.stonks.market.AccountSummary;
import org.ivcode.stonks.market.ChartTick;
import org.ivcode.stonks.market.MarketStatus;
import org.ivcode.stonks.market.OrderAction;
import org.ivcode.stonks.market.OrderDuration;
import org.ivcode.stonks.market.OrderMarket;
import org.ivcode.stonks.market.Tick;
import org.ivcode.stonks.market.utils.AccountSummaryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An investor that weighs a number different indicators ({@link We}
 * 
 * @author isaiah
 *
 */
public class WeightedInvestor extends Investor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WeightedInvestor.class);

	private final GraphBuilder graph;

	private final Double tradeMax;
	private final HistoryContext historyContext;
	private final TradeGovernor tradeGovernor;
	private final Collection<WeightedRating> ratings;

	private final double weightSum;
	
	//private Double recoveryCost;
	//private Integer recoveryQuantity;

	public WeightedInvestor(Double tradeMax, GraphBuilder graph, HistoryContext historyContext,
			TradeGovernor tradeGovernor, Collection<WeightedRating> ratings) {

		this.tradeMax = tradeMax;
		this.graph = graph;
		this.historyContext = historyContext;
		this.tradeGovernor = tradeGovernor;
		this.ratings = ratings;

		double weightSum = 0.0;
		for (WeightedRating rating : ratings) {
			weightSum += rating.getWeight();
		}

		this.weightSum = weightSum;
	}

	public WeightedInvestor(Double tradeMax, GraphBuilder graph, HistoryContext historyContext,
			TradeGovernor tradeGovernor, WeightedRating... ratings) {
		this(tradeMax, graph, historyContext, tradeGovernor, Arrays.asList(ratings));
	}

	@Override
	public void onTick(String symbol, Tick tick) throws IOException {
		MarketStatus status = this.getMarketTime().getMarketStatus();
		if (!MarketStatus.OPEN.equals(status)) {
			return;
		}
		graph.next(tick.getTime());
		graph.withPoint(GraphBuilderFactory.PRICE_NAME, tick.getPrice());

		double value = 0.0;
		for (WeightedRating rating : ratings) {
			value += rating.getWeight() * rating.getRating(tick);
		}

		double qualityRating = value / weightSum;
		graph.withPoint(GraphBuilderFactory.RATING_NAME, qualityRating);
		
		/*
		if(recoveryCost!=null) {
			if(recoveryCost<=tick.getPrice()*recoveryQuantity) {
				this.getBroker().submitOrder(new OrderMarket(tick.getSymbol(), OrderAction.SELL, OrderDuration.DAY, recoveryQuantity));
				graph.withGraphNote("SELL", "price=" + tick.getPrice() + "; quantity=" + recoveryQuantity);
				this.recoveryCost = null;
				this.recoveryQuantity = null;
			}
			
			return;
		}
		*/

		int tradeQuantity = tradeGovernor.getQuantity(qualityRating, tick);
		
		LOGGER.trace("symbol={} : price={} : rating={} : quantity={}", tick.getSymbol(), tick.getPrice(), qualityRating, tradeQuantity);
		
		if (qualityRating >= 0) {
			if (tradeQuantity == 0) {
				return;
			}

			// Buy
			int q = getMaxBuyQuantity(tick);
			q = q < tradeQuantity ? q : tradeQuantity;

			if (q > 0) {
				this.getBroker().submitOrder(new OrderMarket(tick.getSymbol(), OrderAction.BUY, OrderDuration.DAY, q));
				LOGGER.trace("BUY : symbol={} : price={} : rating={} : quantity={}", tick.getSymbol(), tick.getPrice(), qualityRating, tradeQuantity);
				graph.withGraphNote("BUY", "price=" + tick.getPrice() + "; quantity=" + q);
			}
		} else if (qualityRating <= 0) {
			if (tradeQuantity == 0) {
				return;
			}

			// Sell
			int q = getMaxSellQuantity(tick);
			q = q < tradeQuantity ? q : tradeQuantity;

			if (q > 0) {
				this.getBroker().submitOrder(new OrderMarket(tick.getSymbol(), OrderAction.SELL, OrderDuration.DAY, q));
				LOGGER.trace("SELL : symbol={} : price={} : rating={} : quantity={}", tick.getSymbol(), tick.getPrice(), qualityRating, tradeQuantity);
				graph.withGraphNote("SELL", "price=" + tick.getPrice() + "; quantity=" + q);
			}
		}		
	}

	@Override
	public void init(String symbol) throws IOException {
		List<ChartTick> history = this.getChartService().getChart(symbol, historyContext.getRange(),
				historyContext.getInterval(), historyContext.isIncludePerPost());

		tradeGovernor.init(history);

		for (WeightedRating rating : ratings) {
			rating.init(history);
		}
	}

	private int getMaxBuyQuantity(Tick tick) throws IOException {
		AccountSummary summary = this.getBroker().getAccountSummary();
		double cash = summary.getTradableFunds();
		double stockCost = getStockCost(summary);
		
		double tradeableCash = tradeMax==null ? cash : Math.min(tradeMax-stockCost, cash);
		
		return (int) (tradeableCash / tick.getPrice());
	}

	private int getMaxSellQuantity(Tick tick) throws IOException {
		AccountSummary summary = this.getBroker().getAccountSummary();
		int quantity = AccountSummaryUtils.getQuantity(summary, tick.getSymbol());
		
		double cost = AccountSummaryUtils.getStockCost(summary, tick.getSymbol());
		if(cost > tick.getPrice() * quantity) {
			return 0;
		}

		return quantity;
	}

	@Override
	public <T, R> R visit(InvestorVisitor<T, R> vistor, T argument) {
		return vistor.apply(this, argument);
	}

	public Collection<WeightedRating> getRatings() {
		return ratings;
	}

	public TradeGovernor getTradeGovernor() {
		return tradeGovernor;
	}

	public Double getTradeMax() {
		return tradeMax;
	}

	private double getStockCost(AccountSummary summary) {
		return AccountSummaryUtils.getStockCost(summary, super.getSymbol());
	}
}
