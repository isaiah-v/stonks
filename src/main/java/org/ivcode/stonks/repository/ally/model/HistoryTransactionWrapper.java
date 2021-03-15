package org.ivcode.stonks.repository.ally.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class HistoryTransactionWrapper {

	@XmlElement(name = "activity")
	private String activity;

	@XmlElement(name = "amount")
	private Double amount;

	@XmlElement(name = "date")
	private Date date;

	@XmlElement(name = "desc")
	private String desc;

	@XmlElement(name = "symbol")
	private String symbol;

	@XmlElement(name = "transaction")
	private HistoryTransaction transaction;

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public HistoryTransaction getTransaction() {
		return transaction;
	}

	public void setTransaction(HistoryTransaction transaction) {
		this.transaction = transaction;
	}

}
