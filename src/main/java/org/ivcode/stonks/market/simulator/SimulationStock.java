package org.ivcode.stonks.market.simulator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ivcode.stonks.market.Stock;

public class SimulationStock extends Stock {

	private final double listPrice;
	private final double change;
	private final double marketValue;

	public SimulationStock(String symbol, int totalQuantity, double averagePrice,
			double listPrice) {
		super(symbol, totalQuantity, averagePrice, listPrice);
		this.listPrice = listPrice;
		this.change = (listPrice - averagePrice) / averagePrice;
		this.marketValue = totalQuantity * listPrice;
	}

	public double getListPrice() {
		return listPrice;
	}

	public double getChange() {
		return change;
	}

	public double getMarketValue() {
		return marketValue;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
