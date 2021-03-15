package org.ivcode.stonks.properties.model;

import java.util.List;

import org.ivcode.stonks.service.investor.model.InvestorDto;

public class StonkProperties {
	private Double tradeMax;
	private List<InvestorDto> investorDto;
	

	public Double getTradeMax() {
		return tradeMax;
	}

	public void setTradeMax(Double tradeMax) {
		this.tradeMax = tradeMax;
	}

	public List<InvestorDto> getInvestorDto() {
		return investorDto;
	}

	public void setInvestorDto(List<InvestorDto> investorDto) {
		this.investorDto = investorDto;
	}
}
