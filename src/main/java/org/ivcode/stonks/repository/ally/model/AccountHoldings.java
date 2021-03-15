package org.ivcode.stonks.repository.ally.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AccountHoldings {

	@XmlElement(name = "displaydata")
	private AccountDisplayData displayData;

	@XmlElement(name = "holding")
	private List<AccountHolding> holdings;

	@XmlElement(name = "totalsecurities")
	private Double totalSecurities;

	public AccountDisplayData getDisplayData() {
		return displayData;
	}

	public void setDisplayData(AccountDisplayData displayData) {
		this.displayData = displayData;
	}

	public Double getTotalSecurities() {
		return totalSecurities;
	}

	public void setTotalSecurities(Double totalSecurities) {
		this.totalSecurities = totalSecurities;
	}

	public List<AccountHolding> getHoldings() {
		return holdings;
	}

	public void setHoldings(List<AccountHolding> holdings) {
		this.holdings = holdings;
	}

}
