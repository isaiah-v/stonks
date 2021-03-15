package org.ivcode.stonks.repository.ally.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class HistorySecurity {
	@XmlElement(name = "cusip")
	private String cusip;

	@XmlElement(name = "id")
	private String id;

	@XmlElement(name = "sectyp")
	private String sectyp;

	@XmlElement(name = "sym")
	private String sym;

	public String getCusip() {
		return cusip;
	}

	public void setCusip(String cusip) {
		this.cusip = cusip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSectyp() {
		return sectyp;
	}

	public void setSectyp(String sectyp) {
		this.sectyp = sectyp;
	}

	public String getSym() {
		return sym;
	}

	public void setSym(String sym) {
		this.sym = sym;
	}

}
