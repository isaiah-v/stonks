package org.ivcode.stonks.repository.ally.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class HistoryTransaction {

	@XmlElement(name = "accounttype")
	private Integer accountType;

	@XmlElement(name = "commission")
	private Double commission;

	@XmlElement(name = "description")
	private String description;

	@XmlElement(name = "fee")
	private Double fee;

	@XmlElement(name = "price")
	private Double price;

	@XmlElement(name = "quantity")
	private Integer quantity;

	@XmlElement(name = "secfee")
	private Double secfee;

	@XmlElement(name = "security")
	private HistorySecurity security;

	@XmlElement(name = "settlementdate")
	private Date settlementDate;

	@XmlElement(name = "side")
	private Integer side;

	@XmlElement(name = "source")
	private String source;

	@XmlElement(name = "tradedate")
	private Date tradeDate;

	@XmlElement(name = "transactionid")
	private String transactionId;

	@XmlElement(name = "transactiontype")
	private String transactionType;

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getSecfee() {
		return secfee;
	}

	public void setSecfee(Double secfee) {
		this.secfee = secfee;
	}

	public HistorySecurity getSecurity() {
		return security;
	}

	public void setSecurity(HistorySecurity security) {
		this.security = security;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public Integer getSide() {
		return side;
	}

	public void setSide(Integer side) {
		this.side = side;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
}
