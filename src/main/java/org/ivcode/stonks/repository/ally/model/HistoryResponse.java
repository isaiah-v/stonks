package org.ivcode.stonks.repository.ally.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class HistoryResponse {
	@XmlElement
	private Integer elapsedtime;

	@XmlElement
	private String error;

	@XmlElementWrapper(name = "transactions")
	@XmlElement(name = "transaction")
	private List<HistoryTransactionWrapper> transactions;

	public Integer getElapsedtime() {
		return elapsedtime;
	}

	public void setElapsedtime(Integer elapsedtime) {
		this.elapsedtime = elapsedtime;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<HistoryTransactionWrapper> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<HistoryTransactionWrapper> transactions) {
		this.transactions = transactions;
	}

}
