package org.ivcode.stonks.market.simulator.account.funds;

import java.time.LocalDate;

public class FundSummary {

	private final double total;
	private final double cash;
	private final double unsettled;
	private final LocalDate settleDate;
	private final LocalDate dateOfSummary;

	public FundSummary(double total, double cash, double unsettled, LocalDate settleDate, LocalDate dateOfSummary) {
		this.total = total;
		this.cash = cash;
		this.unsettled = unsettled;
		this.settleDate = settleDate;
		this.dateOfSummary = dateOfSummary;
	}

	public double getTotal() {
		return total;
	}

	public double getCash() {
		return cash;
	}

	public double getUnsettled() {
		return unsettled;
	}

	public LocalDate getSettleDate() {
		return settleDate;
	}

	public LocalDate getDateOfSummary() {
		return dateOfSummary;
	}
}
