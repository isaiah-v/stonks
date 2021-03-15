package org.ivcode.stonks.repository.ally.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AccountHoldingQuote {

	@XmlElement(name = "change")
	private Double change;

	@XmlElement(name = "lastprice")
	private Double lastprice;

	public Double getChange() {
		return change;
	}

	public void setChange(Double change) {
		this.change = change;
	}

	public Double getLastprice() {
		return lastprice;
	}

	public void setLastprice(Double lastprice) {
		this.lastprice = lastprice;
	}

}
