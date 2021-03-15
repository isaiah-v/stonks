package org.ivcode.stonks.repository.ally.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AccountHolding {

	@XmlElement(name = "accounttype")
	private Integer accountType;

	@XmlElement(name = "costbasis")
	private Double costBasis;

	@XmlElement(name = "displaydata")
	private AccountHoldingDisplayData displayData;

	@XmlElement(name = "gainloss")
	private Double gainLoss;

	@XmlElement(name = "instrument")
	private AccountHoldingInstrument instrument;

	@XmlElement(name = "marketvalue")
	private Double marketValue;

	@XmlElement(name = "marketvaluechange")
	private Double marketValueChange;

	@XmlElement(name = "price")
	private Double price;

	@XmlElement(name = "purchaseprice")
	private Double purchasePrice;

	@XmlElement(name = "qty")
	private Integer quantity;

	@XmlElement(name = "quote")
	private AccountHoldingQuote quote;

	@XmlElement(name = "sodcostbasis")
	private Double startOfDayCostBasis;

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public Double getCostBasis() {
		return costBasis;
	}

	public void setCostBasis(Double costBasis) {
		this.costBasis = costBasis;
	}

	public AccountHoldingDisplayData getDisplayData() {
		return displayData;
	}

	public void setDisplayData(AccountHoldingDisplayData displayData) {
		this.displayData = displayData;
	}

	public Double getGainLoss() {
		return gainLoss;
	}

	public void setGainLoss(Double gainLoss) {
		this.gainLoss = gainLoss;
	}

	public AccountHoldingInstrument getInstrument() {
		return instrument;
	}

	public void setInstrument(AccountHoldingInstrument instrument) {
		this.instrument = instrument;
	}

	public Double getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(Double marketValue) {
		this.marketValue = marketValue;
	}

	public Double getMarketValueChange() {
		return marketValueChange;
	}

	public void setMarketValueChange(Double marketValueChange) {
		this.marketValueChange = marketValueChange;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public AccountHoldingQuote getQuote() {
		return quote;
	}

	public void setQuote(AccountHoldingQuote quote) {
		this.quote = quote;
	}

	public Double getStartOfDayCostBasis() {
		return startOfDayCostBasis;
	}

	public void setStartOfDayCostBasis(Double startOfDayCostBasis) {
		this.startOfDayCostBasis = startOfDayCostBasis;
	}

}
