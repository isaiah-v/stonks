package org.ivcode.stonks.repository.ally.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AccountHoldingInstrument {

	@XmlElement(name = "cusip")
	private String cusip;

	@XmlElement(name = "description")
	private String description;

	@XmlElement(name = "matdt")
	private String maturityDate;

	@XmlElement(name = "mult")
	private Integer multiplier;

	@XmlElement(name = "putcall")
	private Integer putCall;

	@XmlElement(name = "sectyp")
	private String securityType;

	@XmlElement(name = "strkpx")
	private String strikePrice;

	@XmlElement(name = "sym")
	private String symbol;

	public String getCusip() {
		return cusip;
	}

	public void setCusip(String cusip) {
		this.cusip = cusip;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	public Integer getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(Integer multiplier) {
		this.multiplier = multiplier;
	}

	public Integer getPutCall() {
		return putCall;
	}

	public void setPutCall(Integer putCall) {
		this.putCall = putCall;
	}

	public String getSecurityType() {
		return securityType;
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	public String getStrikePrice() {
		return strikePrice;
	}

	public void setStrikePrice(String strikePrice) {
		this.strikePrice = strikePrice;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
