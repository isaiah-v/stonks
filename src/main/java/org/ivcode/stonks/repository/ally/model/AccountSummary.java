package org.ivcode.stonks.repository.ally.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AccountSummary {

	@XmlElement(name = "account")
	private String account;

	@XmlElement(name = "accountname")
	private String accountName;

	@XmlElement(name = "accountbalance")
	private AccountBalance accountBalance;

	@XmlElement(name = "accountholdings")
	private AccountHoldings accountHoldings;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
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

}
