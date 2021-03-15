package org.ivcode.stonks.repository.ally.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AccountBuyingPower {

	@XmlElement(name = "cashavailableforwithdrawal")
	private Double cashAvailableForWithdrawal;

	@XmlElement(name = "daytrading")
	private Double dayTrading;

	@XmlElement(name = "equitypercentage")
	private Double equityPercentage;

	@XmlElement(name = "options")
	private Double options;

	@XmlElement(name = "soddaytrading")
	private Double startOfDayDayTrading;

	@XmlElement(name = "sodoptions")
	private Double startOfDayOptions;

	@XmlElement(name = "sodstock")
	private Double startOfDayStocks;

	@XmlElement(name = "stock")
	private Double stock;

	public Double getCashAvailableForWithdrawal() {
		return cashAvailableForWithdrawal;
	}

	public void setCashAvailableForWithdrawal(Double cashAvailableForWithdrawal) {
		this.cashAvailableForWithdrawal = cashAvailableForWithdrawal;
	}

	public Double getDayTrading() {
		return dayTrading;
	}

	public void setDayTrading(Double dayTrading) {
		this.dayTrading = dayTrading;
	}

	public Double getEquityPercentage() {
		return equityPercentage;
	}

	public void setEquityPercentage(Double equityPercentage) {
		this.equityPercentage = equityPercentage;
	}

	public Double getOptions() {
		return options;
	}

	public void setOptions(Double options) {
		this.options = options;
	}

	public Double getStartOfDayDayTrading() {
		return startOfDayDayTrading;
	}

	public void setStartOfDayDayTrading(Double startOfDayDayTrading) {
		this.startOfDayDayTrading = startOfDayDayTrading;
	}

	public Double getStartOfDayOptions() {
		return startOfDayOptions;
	}

	public void setStartOfDayOptions(Double startOfDayOptions) {
		this.startOfDayOptions = startOfDayOptions;
	}

	public Double getStartOfDayStocks() {
		return startOfDayStocks;
	}

	public void setStartOfDayStocks(Double startOfDayStocks) {
		this.startOfDayStocks = startOfDayStocks;
	}

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

}
