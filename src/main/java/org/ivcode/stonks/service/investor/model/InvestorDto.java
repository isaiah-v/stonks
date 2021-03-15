package org.ivcode.stonks.service.investor.model;

import java.util.ArrayList;
import java.util.List;

public class InvestorDto {
	private String symbol;
	private Double tradeMax;
	private DayTradeGovernorDto dayTradeGovernor;
	private SlopeTradeGovernorDto slopeTradeGovernor;
	private List<WilliamsPercentRatingDto> williamsPercentRating = new ArrayList<>();
	private List<VariableMovingAverageDto> variableMovingAverage = new ArrayList<>();

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Double getTradeMax() {
		return tradeMax;
	}

	public void setTradeMax(Double tradeMax) {
		this.tradeMax = tradeMax;
	}

	public DayTradeGovernorDto getDayTradeGovernor() {
		return dayTradeGovernor;
	}

	public void setDayTradeGovernor(DayTradeGovernorDto dayTradeGovernor) {
		this.dayTradeGovernor = dayTradeGovernor;
	}

	public SlopeTradeGovernorDto getSlopeTradeGovernor() {
		return slopeTradeGovernor;
	}

	public void setSlopeTradeGovernor(SlopeTradeGovernorDto slopeTradeGovernor) {
		this.slopeTradeGovernor = slopeTradeGovernor;
	}

	public List<WilliamsPercentRatingDto> getWilliamsPercentRating() {
		return williamsPercentRating;
	}

	public void setWilliamsPercentRating(List<WilliamsPercentRatingDto> williamsPercentRating) {
		this.williamsPercentRating = williamsPercentRating;
	}

	public List<VariableMovingAverageDto> getVariableMovingAverage() {
		return variableMovingAverage;
	}

	public void setVariableMovingAverage(List<VariableMovingAverageDto> variableMovingAverage) {
		this.variableMovingAverage = variableMovingAverage;
	}
}
