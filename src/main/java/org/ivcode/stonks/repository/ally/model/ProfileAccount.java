package org.ivcode.stonks.repository.ally.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProfileAccount {
	private Integer account;
	private Boolean fundtrading;
	private Boolean ira;
	private Boolean margintrading;
	private String nickname;
	private Integer optionlevel;
	private Boolean shared;
	private Boolean stocktrading;

	public Integer getAccount() {
		return account;
	}

	public void setAccount(Integer account) {
		this.account = account;
	}

	public Boolean getFundtrading() {
		return fundtrading;
	}

	public void setFundtrading(Boolean fundtrading) {
		this.fundtrading = fundtrading;
	}

	public Boolean getIra() {
		return ira;
	}

	public void setIra(Boolean ira) {
		this.ira = ira;
	}

	public Boolean getMargintrading() {
		return margintrading;
	}

	public void setMargintrading(Boolean margintrading) {
		this.margintrading = margintrading;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getOptionlevel() {
		return optionlevel;
	}

	public void setOptionlevel(Integer optionlevel) {
		this.optionlevel = optionlevel;
	}

	public Boolean getShared() {
		return shared;
	}

	public void setShared(Boolean shared) {
		this.shared = shared;
	}

	public Boolean getStocktrading() {
		return stocktrading;
	}

	public void setStocktrading(Boolean stocktrading) {
		this.stocktrading = stocktrading;
	}

}
