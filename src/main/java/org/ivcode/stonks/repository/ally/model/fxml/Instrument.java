package org.ivcode.stonks.repository.ally.model.fxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class Instrument {

	@XmlAttribute(name = "Desc")
	private String securityDesc;

	@XmlJavaTypeAdapter(SecurityTypeAdapter.class)
	@XmlAttribute(name = "SecTyp")
	private SecurityType securityType;

	@XmlAttribute(name = "Sym")
	private String symbol;

	public String getSecurityDesc() {
		return securityDesc;
	}

	public void setSecurityDesc(String securityDesc) {
		this.securityDesc = securityDesc;
	}

	public SecurityType getSecurityType() {
		return securityType;
	}

	public void setSecurityType(SecurityType securityType) {
		this.securityType = securityType;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
