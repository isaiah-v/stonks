package org.ivcode.stonks.market.simulator.account;

import org.ivcode.stonks.market.Stock;

class AccountStock extends Stock {

	private final int unsettledQuantity;

	public AccountStock(String symbol, int quantity, int unsettledQuantity, double averagePrice, double cost) {
		super(symbol, quantity, averagePrice, cost);
		this.unsettledQuantity = unsettledQuantity;
	}

	public int getUnsettledQuantity() {
		return unsettledQuantity;
	}

}
