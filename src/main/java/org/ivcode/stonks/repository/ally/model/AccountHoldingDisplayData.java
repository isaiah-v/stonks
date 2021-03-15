package org.ivcode.stonks.repository.ally.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AccountHoldingDisplayData {
	@XmlElement(name = "accounttype")
	private String accountType;

	@XmlElement(name = "assetclass")
	private String assetClass;

	@XmlElement(name = "change")
	private String change;

	@XmlElement(name = "costbasis")
	private String costBasis;

	@XmlElement(name = "desc")
	private String description;

	@XmlElement(name = "lastprice")
	private String lastPrice;

	@XmlElement(name = "marketvalue")
	private String marketValue;

	@XmlElement(name = "marketvaluechange")
	private String marketValueChange;

	@XmlElement(name = "qty")
	private String quantity;

	@XmlElement(name = "symbol")
	private String symbol;

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAssetClass() {
		return assetClass;
	}

	public void setAssetClass(String assetClass) {
		this.assetClass = assetClass;
	}

	public String getChange() {
		return change;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public String getCostBasis() {
		return costBasis;
	}

	public void setCostBasis(String costBasis) {
		this.costBasis = costBasis;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(String lastPrice) {
		this.lastPrice = lastPrice;
	}

	public String getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}

	public String getMarketValueChange() {
		return marketValueChange;
	}

	public void setMarketValueChange(String marketValueChange) {
		this.marketValueChange = marketValueChange;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
