package org.ivcode.stonks.repository.ally.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AccountMoney {

	@XmlElement(name = "accruedinterest")
	private Double accruedInterest;

	@XmlElement(name = "cash")
	private Double cash;

	@XmlElement(name = "cashavailable")
	private Double cashAvailable;

	@XmlElement(name = "marginbalance")
	private Double marginBalance;

	@XmlElement(name = "mmf")
	private Double moneyMarketFund;

	@XmlElement(name = "total")
	private Double total;

	@XmlElement(name = "uncleareddeposits")
	private Double unclearedDeposits;

	@XmlElement(name = "unsettledfunds")
	private Double unsettledFunds;

	@XmlElement(name = "yield")
	private Double yield;

	public Double getAccruedInterest() {
		return accruedInterest;
	}

	public void setAccruedInterest(Double accruedInterest) {
		this.accruedInterest = accruedInterest;
	}

	public Double getCash() {
		return cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}

	public Double getCashAvailable() {
		return cashAvailable;
	}

	public void setCashAvailable(Double cashAvailable) {
		this.cashAvailable = cashAvailable;
	}

	public Double getMarginBalance() {
		return marginBalance;
	}

	public void setMarginBalance(Double marginBalance) {
		this.marginBalance = marginBalance;
	}

	public Double getMoneyMarketFund() {
		return moneyMarketFund;
	}

	public void setMoneyMarketFund(Double moneyMarketFund) {
		this.moneyMarketFund = moneyMarketFund;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getUnclearedDeposits() {
		return unclearedDeposits;
	}

	public void setUnclearedDeposits(Double unclearedDeposits) {
		this.unclearedDeposits = unclearedDeposits;
	}

	public Double getUnsettledFunds() {
		return unsettledFunds;
	}

	public void setUnsettledFunds(Double unsettledFunds) {
		this.unsettledFunds = unsettledFunds;
	}

	public Double getYield() {
		return yield;
	}

	public void setYield(Double yield) {
		this.yield = yield;
	}

}
