package org.ivcode.stonks.market.common;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ivcode.stonks.market.AccountSummary;
import org.ivcode.stonks.market.Stock;

public class BasicAccountSummary implements AccountSummary {

	private String id;
	private double availableCash;
	private double tradableFunds;
	private double unsettledFunds;
	private List<Stock> holdings;
	
	public BasicAccountSummary() {
	}
	
	public BasicAccountSummary(String id, double availableCash, double tradableFunds, double unsettledFunds, List<Stock> holdings) {
		this.id = id;
		this.tradableFunds = tradableFunds;
		this.availableCash = availableCash;
		this.unsettledFunds = unsettledFunds;
		this.holdings = holdings;
	}
	
	public BasicAccountSummary(AccountSummary accountSummary) {
		this.id = accountSummary.getId();
		this.availableCash = accountSummary.getAvailableCash();
		this.unsettledFunds = accountSummary.getUnsettledFunds();
		this.holdings = accountSummary.getHoldings();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public double getAvailableCash() {
		return availableCash;
	}

	@Override
	public double getUnsettledFunds() {
		return unsettledFunds;
	}

	@Override
	public List<Stock> getHoldings() {
		return holdings;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAvailableCash(double availableCash) {
		this.availableCash = availableCash;
	}

	public void setTradableFunds(double tradableFunds) {
		this.tradableFunds = tradableFunds;
	}

	public void setUnsettledFunds(double unsettledFunds) {
		this.unsettledFunds = unsettledFunds;
	}

	public void setHoldings(List<Stock> holdings) {
		this.holdings = holdings;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

	@Override
	public double getTradableFunds() {
		return tradableFunds;
	}
}
