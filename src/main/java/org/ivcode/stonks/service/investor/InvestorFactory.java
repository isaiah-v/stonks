package org.ivcode.stonks.service.investor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ivcode.stonks.graph.GraphBuilder;
import org.ivcode.stonks.graph.GraphBuilderNop;
import org.ivcode.stonks.indicator.LookbackContext;
import org.ivcode.stonks.invester.Investor;
import org.ivcode.stonks.invester.ratings.VariableMovingAverageRating;
import org.ivcode.stonks.invester.ratings.WilliamsPercentRangeRating;
import org.ivcode.stonks.invester.tradegov.DayTradeGovernor;
import org.ivcode.stonks.invester.tradegov.LRSlopeTradeGovernor;
import org.ivcode.stonks.invester.tradegov.TradeGovernor;
import org.ivcode.stonks.invester.weighted.HistoryContext;
import org.ivcode.stonks.invester.weighted.WeightedInvestor;
import org.ivcode.stonks.invester.weighted.WeightedRating;
import org.ivcode.stonks.market.ChartInterval;
import org.ivcode.stonks.market.ChartRange;
import org.ivcode.stonks.service.investor.model.DayTradeGovernorDto;
import org.ivcode.stonks.service.investor.model.InvestorDto;
import org.ivcode.stonks.service.investor.model.SlopeTradeGovernorDto;
import org.ivcode.stonks.service.investor.model.VariableMovingAverageDto;
import org.ivcode.stonks.service.investor.model.WilliamsPercentRatingDto;

public class InvestorFactory {
	
	private static final HistoryContext DEFAULT_HISTORY = new HistoryContext(ChartRange.ONE_MONTH, ChartInterval.FIVE_MINUTE, false);
	
	public static InvestorDto createInvestorDto(String symbol, Investor investor) {
		return investor.visit(new InvestorDtoVisitor(), symbol);
	}
	
	public static Investor createInvestor(InvestorDto dto) {
		return createInvestor(dto, GraphBuilderNop.INSTANCE);
	}
	
	public static Investor createInvestor(InvestorDto dto, GraphBuilder graph) {		
		List<WeightedRating> ratings = new ArrayList<>();
		ratings.addAll(createWilliamsPercentRating(dto, graph));
		ratings.addAll(createVariableMovingAverage(dto, graph));
		
		TradeGovernor tradeGov = createTradeGovernor(dto, graph);
		
		return new WeightedInvestor(dto.getTradeMax(), graph, DEFAULT_HISTORY, tradeGov, ratings);
	}
	
	private static List<WeightedRating> createWilliamsPercentRating(InvestorDto dto, GraphBuilder graph) {
		List<WilliamsPercentRatingDto> list = dto.getWilliamsPercentRating();
		if(list==null) {
			return Collections.emptyList();
		}
		
		List<WeightedRating> ratings = new ArrayList<>();
		
		for(WilliamsPercentRatingDto will : list) {
			LookbackContext lookback = new LookbackContext(will.getLookback(), will.getLookbackDuration(), will.getLookbackUnit());
			WilliamsPercentRangeRating r = new WilliamsPercentRangeRating(will.getName(), will.getOversold(), will.getOverbought(), lookback, graph);
			WeightedRating wr = new WeightedRating(r, will.getWeight());
			
			ratings.add(wr);
		}
		
		return ratings;
	}
	
	private static List<WeightedRating> createVariableMovingAverage(InvestorDto dto, GraphBuilder graph) {
		List<VariableMovingAverageDto> list = dto.getVariableMovingAverage();
		if(list==null) {
			return Collections.emptyList();
		}
		
		List<WeightedRating> ratings = new ArrayList<>();
		
		for(VariableMovingAverageDto vma : list) {
			LookbackContext lookback = new LookbackContext(vma.getLookback(), vma.getLookbackDuration(), vma.getLookbackUnit());
			VariableMovingAverageRating r = new VariableMovingAverageRating(vma.getName(), vma.getBound(), lookback, graph);
			WeightedRating wr = new WeightedRating(r, vma.getWeight());
			
			ratings.add(wr);
		}
		
		return ratings;
	}
	
	private static TradeGovernor createTradeGovernor(InvestorDto dto, GraphBuilder graph) {
		TradeGovernor tradeGov1 = createTradeGovernor(dto.getSlopeTradeGovernor(), graph);
		TradeGovernor tradeGov2 = createTradeGovernor(dto.getDayTradeGovernor());
		
		TradeGovernor tradeGov = null;
		if(tradeGov1==null ^ tradeGov2==null) {
			tradeGov = tradeGov1==null ? tradeGov2 : tradeGov1;
		}
		
		// if null, then more than one or none were defined 
		if(tradeGov==null) {
			throw new IllegalArgumentException("exactly one trade governor must be defined");
		}
		
		return tradeGov;
	}
	
	private static TradeGovernor createTradeGovernor(SlopeTradeGovernorDto dto, GraphBuilder graph) {
		if(dto==null) {
			return null;
		}
		
		LookbackContext lookback = new LookbackContext(dto.getLookback(), dto.getLookbackDuration(), dto.getLookbackUnit());
		return new LRSlopeTradeGovernor(dto.getName(), dto.getBuyTrades(), dto.getSellTrades(), dto.getLower(), dto.getUpper(), lookback, dto.getWaitTime(), graph);
	}
	
	public static TradeGovernor createTradeGovernor(DayTradeGovernorDto dto) {
		if(dto==null) {
			return null;
		}
		
		return new DayTradeGovernor(dto.getBuyQuantity(), dto.getSellQuantity());
	}
}
