package org.ivcode.stonks.service.investor.model;

public class DayTradeGovernorDto {
	private int buyQuantity;
	private int sellQuantity = Integer.MAX_VALUE;

	public int getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(int buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

	public int getSellQuantity() {
		return sellQuantity;
	}

	public void setSellQuantity(int sellQuantity) {
		this.sellQuantity = sellQuantity;
	}

}
