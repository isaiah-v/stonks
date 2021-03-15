package org.ivcode.stonks.invester.weighted;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.ivcode.stonks.graph.GraphBuilderNop;
import org.ivcode.stonks.invester.ratings.RatingIndicator;
import org.ivcode.stonks.invester.ratings.VariableMovingAverageRating;
import org.ivcode.stonks.invester.ratings.WilliamsPercentRangeRating;
import org.ivcode.stonks.invester.ratings.iterable.RatingsIterables;
import org.ivcode.stonks.invester.tradegov.SimpleTradeGovernor;
import org.ivcode.stonks.invester.tradegov.TradeGovernor;
import org.ivcode.stonks.market.ChartInterval;
import org.ivcode.stonks.market.ChartRange;
import org.ivcode.stonks.market.Quote;
import org.ivcode.stonks.market.live.YahooQuoteTransformer;
import org.ivcode.stonks.market.simulator.Report;
import org.ivcode.stonks.market.simulator.SimulationContext;
import org.ivcode.stonks.market.simulator.SimulationProcessor;
import org.ivcode.stonks.repository.yahoo.v8.model.YahooQuote;
import org.ivcode.stonks.service.yahoo.v8.YahooService;
import org.ivcode.stonks.utils.Factory;
import org.ivcode.stonks.utils.iterators.DoubleIterable;
import org.ivcode.stonks.utils.iterators.TypedCombinationIterable;

public class WeightedSimulationProcessor {
	
	private static final double TRADE_FACTOR = 0.75;
	
	private final YahooService yahooService;
	private final SimulationContext context;
	
	public WeightedSimulationProcessor(SimulationContext context, YahooService yahooService) {
		this.context = context;
		this.yahooService = yahooService;
	}
	
	public Report run() throws InterruptedException, ExecutionException, IOException {
		int trades = getTades();
		double tradeRate = getTradeRate();
		
		//return runVma(trades, tradeRate);
		return runWilliams(trades, tradeRate);
		
		//RatingIndicatorFactory<?> will = WilliamsPercentRangeRating.createRateableFactory(0.37894736842105339, 0.9368421052631584, 1, TimeUnit.HOURS, 40);
		//RatingIndicatorFactory<?> vma = VariableMovingAverageRating.createRateableFactory(1, TimeUnit.HOURS, 32, 0.03249999999999998);
		
		//return runWeight(trades, tradeRate, Arrays.asList(will, vma));
		
		//RatingIndicatorFactory<WeightedRating> w1 = () -> new WeightedRating(will.createInstance(), 0.8888888888888887);
		//RatingIndicatorFactory<WeightedRating> w2 = () -> new WeightedRating(vma.createInstance(), 0.11111111111111012);
		//return runTradeGovernor(21, Arrays.asList(w1, w2));
		
	}
	
	private Report runWilliams(int trades, double tradeRate) throws InterruptedException, ExecutionException {
		HistoryContext historyContext = new HistoryContext(ChartRange.ONE_MONTH, ChartInterval.ONE_HOUR, false);
		
		Iterable<Factory<WilliamsPercentRangeRating>> i1 = RatingsIterables.williamsRating();
		
		Function<List<Object>, WeightedInvestor> func = l -> {
			try {
				Factory<? extends RatingIndicator> rating = (Factory<? extends RatingIndicator>) l.get(0);
				WeightedRating wRating1 = new WeightedRating(rating.createInstance(), 1);
				
				//TradeGovernor tradeGov = new SimpleTradeGovernor(trades, tradeRate, 8, 0.1);
				TradeGovernor tradeGov = new SimpleTradeGovernor();
			
				return new WeightedInvestor(null, GraphBuilderNop.INSTANCE, historyContext, tradeGov, wRating1);
			} catch (Exception e) {
				return null;
			}
		};
		
		Iterable<WeightedInvestor> it = new TypedCombinationIterable<>(func, i1);
		
		SimulationProcessor processor = new SimulationProcessor(16, context, it);
		return processor.run();
	}
	
	private Report runVma(int trades, double tradeRate) throws InterruptedException, ExecutionException {
		HistoryContext historyContext = new HistoryContext(ChartRange.ONE_MONTH, ChartInterval.ONE_HOUR, false);
		
		Iterable<Factory<VariableMovingAverageRating>> i1 = RatingsIterables.vmr();
		
		Function<List<Object>, WeightedInvestor> func = l -> {
			try {
				Factory<? extends RatingIndicator> rating = (Factory<? extends RatingIndicator>) l.get(0);
				WeightedRating wRating1 = new WeightedRating(rating.createInstance(), 1);
				
				//TradeGovernor tradeGov = new SimpleTradeGovernor(trades, tradeRate, 8, 0.1);
				TradeGovernor tradeGov = new SimpleTradeGovernor();
			
				return new WeightedInvestor(null, GraphBuilderNop.INSTANCE, historyContext, tradeGov, wRating1);
			} catch (Exception e) {
				return null;
			}
		};
		
		Iterable<WeightedInvestor> it = new TypedCombinationIterable<>(func, i1);
		
		SimulationProcessor processor = new SimulationProcessor(8, context, it);
		return processor.run();
	}
	
	private Report runWeight(int trades, double tradeRate, List<Factory<? extends RatingIndicator>> rateables) throws InterruptedException, ExecutionException {
		HistoryContext historyContext = new HistoryContext(ChartRange.ONE_MONTH, ChartInterval.ONE_HOUR, false);
		
		List<Iterable<Double>> iterableList = new ArrayList<Iterable<Double>>();
		for(int i=0; i<rateables.size(); i++) {
			iterableList.add(new DoubleIterable(0, 1, 10));
		}
		
		Function<List<Object>, WeightedInvestor> func = l -> {
			try {
				List<WeightedRating> wInvesterList = new ArrayList<>();
				
				for(int i=0; i<rateables.size(); i++) {
					wInvesterList.add(new WeightedRating(rateables.get(i).createInstance(), (Double)l.get(i)));
				}
				
				//TradeGovernor tradeGov = new SimpleTradeGovernor(trades, tradeRate, 8, 0.1);
				TradeGovernor tradeGov = new SimpleTradeGovernor();
			
				return new WeightedInvestor(null, GraphBuilderNop.INSTANCE, historyContext, tradeGov, wInvesterList);
			} catch (Exception e) {
				return null;
			}
		};
		
		Iterable<WeightedInvestor> it = new TypedCombinationIterable<>(func, iterableList.toArray(new Iterable<?>[iterableList.size()]));
		
		SimulationProcessor processor = new SimulationProcessor(8, context, it);
		return processor.run();
	}
	
	private Report runTradeGovernor(double price, List<Factory<WeightedRating>> rateableIndicators) throws InterruptedException, ExecutionException {
		HistoryContext historyContext = new HistoryContext(ChartRange.ONE_MONTH, ChartInterval.ONE_HOUR, false);
		
		Iterable<Double> tradeFactor = new DoubleIterable(0.0, 1, 20);
		Iterable<Double> bypass = new DoubleIterable(0.0, 0.2, 20);
		
		Function<List<Object>, WeightedInvestor> func = l -> {
			try {
				List<WeightedRating> ratings = rateableIndicators.stream().map(r->r.createInstance()).collect(Collectors.toList());
			
				double factor = (Double)l.get(0);
				double bp = (Double)l.get(1);
				
				//TradeGovernor tradeGov = new SimpleTradeGovernor(getTades(price, factor), getTradeRate(factor), 8, bp);
				TradeGovernor tradeGov = new SimpleTradeGovernor();
				
				return new WeightedInvestor(null, GraphBuilderNop.INSTANCE, historyContext, tradeGov, ratings);
			} catch (Exception e) {
				return null;
			}
		};
		
		Iterable<WeightedInvestor> it = new TypedCombinationIterable<>(func, tradeFactor, bypass);
		
		SimulationProcessor processor = new SimulationProcessor(8, context, it);
		return processor.run();
	}
	
	private int getTades() throws IOException {
		double price = getPrice(this.context.getSymbol());
		return getTades(price, TRADE_FACTOR);
	}
	
	private int getTades(double price, double tradeFactor) throws IOException {
		return (int)Math.round(tradeFactor * (context.getDeposite() / price));
	}
	
	private double getTradeRate() {
		return getTradeRate(TRADE_FACTOR);
	}
	
	private double getTradeRate(double tradeFactor) {
		return 1/tradeFactor;
	}
	
	private double getPrice(String symbol) throws IOException {
		YahooQuote yquote = yahooService.getQuote(Arrays.asList(symbol));
		List<Quote> quotes = YahooQuoteTransformer.toQuotes(yquote);
		Quote quote = quotes.get(0);
		
		return quote.getList();
	}
}
