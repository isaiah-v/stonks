package org.ivcode.stonks.repository.ally.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AccountDisplayData {

	@XmlElement(name = "totalsecurities")
	private String totalSecurities;

	public String getTotalSecurities() {
		return totalSecurities;
	}

	public void setTotalSecurities(String totalSecurities) {
		this.totalSecurities = totalSecurities;
	}

}
