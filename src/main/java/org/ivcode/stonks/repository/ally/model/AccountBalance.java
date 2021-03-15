package org.ivcode.stonks.repository.ally.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AccountBalance {
	@XmlElement(name = "account")
	private String account;

	@XmlElement(name = "accountvalue")
	private Double accountValue;

	@XmlElement(name = "buyingpower")
	private AccountBuyingPower buyingPower;

	@XmlElement(name = "fedcall")
	private Double fedCall;

	@XmlElement(name = "housecall")
	private Double houseCall;

	@XmlElement(name = "money")
	private AccountMoney money;

	@XmlElement(name = "securities")
	private AccountSecurities securities;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Double getAccountValue() {
		return accountValue;
	}

	public void setAccountValue(Double accountValue) {
		this.accountValue = accountValue;
	}

	public AccountBuyingPower getBuyingPower() {
		return buyingPower;
	}

	public void setBuyingPower(AccountBuyingPower buyingPower) {
		this.buyingPower = buyingPower;
	}

	public Double getFedCall() {
		return fedCall;
	}

	public void setFedCall(Double fedCall) {
		this.fedCall = fedCall;
	}

	public Double getHouseCall() {
		return houseCall;
	}

	public void setHouseCall(Double houseCall) {
		this.houseCall = houseCall;
	}

	public AccountMoney getMoney() {
		return money;
	}

	public void setMoney(AccountMoney money) {
		this.money = money;
	}

	public AccountSecurities getSecurities() {
		return securities;
	}

	public void setSecurities(AccountSecurities securities) {
		this.securities = securities;
	}

}
