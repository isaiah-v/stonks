package org.ivcode.stonks.repository.ally.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AccountSecurities {

	@XmlElement(name = "longoptions")
	private Double longOptions;

	@XmlElement(name = "longstocks")
	private Double longStocks;

	@XmlElement(name = "options")
	private Double options;

	@XmlElement(name = "shortoptions")
	private Double shortOptions;

	@XmlElement(name = "shortstocks")
	private Double shortStocks;

	@XmlElement(name = "stocks")
	private Double stocks;

	@XmlElement(name = "total")
	private Double total;

	public Double getLongOptions() {
		return longOptions;
	}

	public void setLongOptions(Double longOptions) {
		this.longOptions = longOptions;
	}

	public Double getLongStocks() {
		return longStocks;
	}

	public void setLongStocks(Double longStocks) {
		this.longStocks = longStocks;
	}

	public Double getOptions() {
		return options;
	}

	public void setOptions(Double options) {
		this.options = options;
	}

	public Double getShortOptions() {
		return shortOptions;
	}

	public void setShortOptions(Double shortOptions) {
		this.shortOptions = shortOptions;
	}

	public Double getShortStocks() {
		return shortStocks;
	}

	public void setShortStocks(Double shortStocks) {
		this.shortStocks = shortStocks;
	}

	public Double getStocks() {
		return stocks;
	}

	public void setStocks(Double stocks) {
		this.stocks = stocks;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
