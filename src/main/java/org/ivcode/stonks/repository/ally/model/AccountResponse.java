package org.ivcode.stonks.repository.ally.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountResponse {
	@XmlAttribute
	private String id;

	@XmlElement
	private Integer elapsedtime;

	@XmlElement(name = "accountbalance")
	private AccountBalance accountBalance;

	@XmlElement(name = "accountholdings")
	private AccountHoldings accountHoldings;

	@XmlElement
	private String error;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getElapsedtime() {
		return elapsedtime;
	}

	public void setElapsedtime(Integer elapsedtime) {
		this.elapsedtime = elapsedtime;
	}

	public AccountBalance getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(AccountBalance accountBalance) {
		this.accountBalance = accountBalance;
	}

	public AccountHoldings getAccountHoldings() {
		return accountHoldings;
	}

	public void setAccountHoldings(AccountHoldings accountHoldings) {
		this.accountHoldings = accountHoldings;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
