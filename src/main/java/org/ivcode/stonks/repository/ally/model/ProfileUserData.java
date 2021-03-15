package org.ivcode.stonks.repository.ally.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProfileUserData {

	@XmlElement
	private ProfileAccount account;

	@XmlElement
	private Boolean disabled;

	@XmlElement
	private Boolean resetpassword;

	@XmlElement
	private Boolean resettradingpassword;

	@XmlElement
	private Boolean realtime;

	@XmlElement
	private String quotebucket;

	@XmlElementWrapper(name = "userprofile")
	@XmlElement(name = "entry")
	private List<Entry> userprofile;

	public ProfileAccount getAccount() {
		return account;
	}

	public void setAccount(ProfileAccount account) {
		this.account = account;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Boolean getResetpassword() {
		return resetpassword;
	}

	public void setResetpassword(Boolean resetpassword) {
		this.resetpassword = resetpassword;
	}

	public Boolean getResettradingpassword() {
		return resettradingpassword;
	}

	public void setResettradingpassword(Boolean resettradingpassword) {
		this.resettradingpassword = resettradingpassword;
	}

	public Boolean getRealtime() {
		return realtime;
	}

	public void setRealtime(Boolean realtime) {
		this.realtime = realtime;
	}

	public String getQuotebucket() {
		return quotebucket;
	}

	public void setQuotebucket(String quotebucket) {
		this.quotebucket = quotebucket;
	}

	public List<Entry> getUserprofile() {
		return userprofile;
	}

	public void setUserprofile(List<Entry> userprofile) {
		this.userprofile = userprofile;
	}

}
