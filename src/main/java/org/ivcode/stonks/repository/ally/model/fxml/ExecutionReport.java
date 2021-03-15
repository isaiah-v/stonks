package org.ivcode.stonks.repository.ally.model.fxml;

import java.time.ZonedDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.ivcode.stonks.repository.ally.model.adapters.ISOZonedDateTimeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class ExecutionReport {

	@XmlAttribute(name = "Txt")
	private String text;

	@XmlJavaTypeAdapter(ISOZonedDateTimeAdapter.class)
	@XmlAttribute(name = "TxnTm")
	private ZonedDateTime transactTime;

	@XmlJavaTypeAdapter(ISOZonedDateTimeAdapter.class)
	@XmlAttribute(name = "TrdDt")
	private ZonedDateTime tradeDate;

	@XmlAttribute(name = "LeavesQty")
	private Double leavesQty;

	@XmlJavaTypeAdapter(TimeInForceAdapter.class)
	@XmlAttribute(name = "TmInForce")
	private TimeInForce timeInForce;

	@XmlAttribute(name = "Px")
	private Double legPrice;

	@XmlJavaTypeAdapter(OrderTypeAdapter.class)
	@XmlAttribute(name = "Typ")
	private OrderType type;

	@XmlJavaTypeAdapter(SideOfOrderAdapter.class)
	@XmlAttribute(name = "Side")
	private SideOfOrder side;

	@XmlJavaTypeAdapter(AccountTypeAdapter.class)
	@XmlAttribute(name = "AcctTyp")
	private AccountType accountType;

	@XmlAttribute(name = "Acct")
	private String account;

	@XmlJavaTypeAdapter(OrdStatusAdapter.class)
	@XmlAttribute(name = "Stat")
	private OrdStatus stat;

	@XmlAttribute(name = "ID")
	private String id;

	@XmlAttribute(name = "OrdID")
	private String orderId;

	@XmlElement(name = "Instrmt", namespace = "http://www.fixprotocol.org/FIXML-5-0-SP2")
	private Instrument instrument;

	@XmlElement(name = "OrdQty", namespace = "http://www.fixprotocol.org/FIXML-5-0-SP2")
	private OrderQuantity orderQuantity;

	@XmlElement(name = "Comm", namespace = "http://www.fixprotocol.org/FIXML-5-0-SP2")
	private CommissionData commissionData;

	public ZonedDateTime getTransactTime() {
		return transactTime;
	}

	public void setTransactTime(ZonedDateTime transactTime) {
		this.transactTime = transactTime;
	}

	public ZonedDateTime getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(ZonedDateTime tradeDate) {
		this.tradeDate = tradeDate;
	}

	public Double getLeavesQty() {
		return leavesQty;
	}

	public void setLeavesQty(Double leavesQty) {
		this.leavesQty = leavesQty;
	}

	public TimeInForce getTimeInForce() {
		return timeInForce;
	}

	public void setTimeInForce(TimeInForce timeInForce) {
		this.timeInForce = timeInForce;
	}

	public Double getLegPrice() {
		return legPrice;
	}

	public void setLegPrice(Double legPrice) {
		this.legPrice = legPrice;
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

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public OrdStatus getStat() {
		return stat;
	}

	public void setStat(OrdStatus stat) {
		this.stat = stat;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public CommissionData getCommissionData() {
		return commissionData;
	}

	public void setCommissionData(CommissionData commissionData) {
		this.commissionData = commissionData;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
