package org.ivcode.stonks.market;

import java.io.IOException;

public interface BrokerService {

	/**
	 * Returns the current account summary. Note that the returned value is a static
	 * model. To get an updated value, you will need to call this methods again.
	 * 
	 * @return The Account Summary
	 * @throws IOException thrown if there's a problem pulling the account summary.
	 */
	public AccountSummary getAccountSummary() throws IOException;

	/**
	 * Used to submit an order with the broker.
	 * 
	 * @param order The order
	 * @throws IOException thrown if there's a problem submitting the order
	 */
	public void submitOrder(Order order) throws IOException;

	/**
	 * Sets a max on the amount that should be invested.
	 * 
	 * @param tradeMax The amount to set. A {@code null} value unsets the trade max
	 */
	public void setTadeMax(Double tradeMax);

	/**
	 * Gets the trade max.
	 * 
	 * @return The trade max. A {@code null} values implies no max is set.
	 */
	public Double getTadeMax();
}
