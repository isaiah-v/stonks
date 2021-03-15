package org.ivcode.stonks.repository.ally.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Order {
	
	@XmlElement
	private String fixmlmessage;

	public String getFixmlmessage() {
		return fixmlmessage;
	}

	public void setFixmlmessage(String fixmlmessage) {
		this.fixmlmessage = fixmlmessage;
	}

}
