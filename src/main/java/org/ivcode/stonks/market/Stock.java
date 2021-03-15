package org.ivcode.stonks.market;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Aggregate information about stock holdings
 * 
 * @author isaiah
 */
public class Stock {

	private final String symbol;
	private final int totalQuantity;
	private final double averagePrice;
	private final double cost;

	public Stock(String symbol, int quantity, double averagePrice, double cost) {
		this.symbol = symbol;
		this.totalQuantity = quantity;
		this.averagePrice = averagePrice;
		this.cost = cost;
	}

	public String getSymbol() {
		return symbol;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public double getAveragePrice() {
		return averagePrice;
	}

	public double getCost() {
		return cost;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}
