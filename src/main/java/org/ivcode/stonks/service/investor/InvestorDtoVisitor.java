package org.ivcode.stonks.service.investor;

import org.ivcode.stonks.invester.InvestorVisitor;
import org.ivcode.stonks.invester.ratings.RatingIndicatorVisitor;
import org.ivcode.stonks.invester.ratings.VariableMovingAverageRating;
import org.ivcode.stonks.invester.ratings.WilliamsPercentRangeRating;
import org.ivcode.stonks.invester.tradegov.DayTradeGovernor;
import org.ivcode.stonks.invester.tradegov.LRSlopeTradeGovernor;
import org.ivcode.stonks.invester.tradegov.TradeGovernorVisitor;
import org.ivcode.stonks.invester.weighted.WeightedInvestor;
import org.ivcode.stonks.invester.weighted.WeightedRating;
import org.ivcode.stonks.service.investor.model.DayTradeGovernorDto;
import org.ivcode.stonks.service.investor.model.InvestorDto;
import org.ivcode.stonks.service.investor.model.SlopeTradeGovernorDto;
import org.ivcode.stonks.service.investor.model.VariableMovingAverageDto;
import org.ivcode.stonks.service.investor.model.WilliamsPercentRatingDto;

class InvestorDtoVisitor implements InvestorVisitor<String, InvestorDto> {

	@Override
	public InvestorDto apply(WeightedInvestor investor, String symbol) {
		InvestorDto dto = new InvestorDto();
		dto.setSymbol(symbol);
		dto.setTradeMax(investor.getTradeMax());
		
		for(WeightedRating wr : investor.getRatings()) {
			InvestorDtoRatingVisitor v = new InvestorDtoRatingVisitor(wr.getWeight());
			wr.getRatingIndicator().visit(v, dto);
		}
		
		investor.getTradeGovernor().visit(new InvestorDtoTradeGov(), dto);
		return dto;
	}

	private static class InvestorDtoRatingVisitor implements RatingIndicatorVisitor<InvestorDto, Void> {
		
		private final double weight;
		
		public InvestorDtoRatingVisitor(double weight) {
			this.weight = weight;
		}

		@Override
		public Void apply(VariableMovingAverageRating rating, InvestorDto argument) {
			VariableMovingAverageDto dto = new VariableMovingAverageDto();
			
			dto.setName(rating.getName());
			dto.setBound(rating.getBound());
			dto.setLookback(rating.getLookback().getLookback());
			dto.setLookbackDuration(rating.getLookback().getLookbackDuration());
			dto.setLookbackUnit(rating.getLookback().getLookbackUnit());
			dto.setWeight(weight);
			
			argument.getVariableMovingAverage().add(dto);
			return null;
		}

		@Override
		public Void apply(WilliamsPercentRangeRating rating, InvestorDto argument) {
			WilliamsPercentRatingDto dto = new WilliamsPercentRatingDto();
			dto.setName(rating.getName());
			dto.setLookback(rating.getLookback().getLookback());
			dto.setLookbackDuration(rating.getLookback().getLookbackDuration());
			dto.setLookbackUnit(rating.getLookback().getLookbackUnit());
			dto.setOverbought(rating.getOverbought());
			dto.setOversold(rating.getOversold());
			dto.setWeight(weight);
			
			argument.getWilliamsPercentRating().add(dto);
			return null;
		}
	}
	
	private static class InvestorDtoTradeGov implements TradeGovernorVisitor<InvestorDto, Void> {

		@Override
		public Void apply(LRSlopeTradeGovernor tradeGovernor, InvestorDto argument) {
			SlopeTradeGovernorDto dto = new SlopeTradeGovernorDto();
			dto.setName(tradeGovernor.getName());
			dto.setBuyTrades(tradeGovernor.getBuyTrades());
			dto.setSellTrades(tradeGovernor.getSellTrades());
			dto.setLower(tradeGovernor.getLower());
			dto.setUpper(tradeGovernor.getUpper());
			dto.setLookback(tradeGovernor.getLookback().getLookback());
			dto.setLookbackDuration(tradeGovernor.getLookback().getLookbackDuration());
			dto.setLookbackUnit(tradeGovernor.getLookback().getLookbackUnit());
			dto.setWaitTime(tradeGovernor.getWaitTime());
			
			argument.setSlopeTradeGovernor(dto);
			return null;
		}

		@Override
		public Void apply(DayTradeGovernor tradeGovernor, InvestorDto argument) {
			DayTradeGovernorDto dto = new DayTradeGovernorDto();
			dto.setBuyQuantity(tradeGovernor.getBuyQuantity());
			
			argument.setDayTradeGovernor(dto);
			return null;
		}
		
	}
}
