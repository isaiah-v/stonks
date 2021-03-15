package org.ivcode.stonks.repository.ally.model.fxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class NewOrder {

	@XmlJavaTypeAdapter(TimeInForceAdapter.class)
	@XmlAttribute(name = "TmInForce")
	private TimeInForce timeInForce;

	@XmlJavaTypeAdapter(OrderTypeAdapter.class)
	@XmlAttribute(name = "Typ")
	private OrderType type;

	@XmlJavaTypeAdapter(SideOfOrderAdapter.class)
	@XmlAttribute(name = "Side")
	private SideOfOrder side;

	@XmlAttribute(name = "Acct")
	private String account;

	@XmlElement(name = "Instrmt", namespace = "http://www.fixprotocol.org/FIXML-5-0-SP2")
	private Instrument instrument;

	@XmlElement(name = "OrdQty", namespace = "http://www.fixprotocol.org/FIXML-5-0-SP2")
	private OrderQuantity orderQuantity;

	public TimeInForce getTimeInForce() {
		return timeInForce;
	}

	public void setTimeInForce(TimeInForce timeInForce) {
		this.timeInForce = timeInForce;
	}

	public OrderType getType() {
		return type;
	}

	public void setType(OrderType type) {
		this.type = type;
	}

	public SideOfOrder getSide() {
		return side;
	}

	public void setSide(SideOfOrder side) {
		this.side = side;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public OrderQuantity getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(OrderQuantity orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

}
