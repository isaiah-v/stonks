package org.ivcode.stonks.repository.ally.model;

import static org.apache.commons.lang3.StringUtils.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public enum ClockMarketStatus  {
	PRE_OPEN ("pre"),
	OPEN("open"),
	AFTER_HOURS("after"),
	CLOSED("close"),
	;

	private final String code;

	private ClockMarketStatus(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	public static String getCode(ClockMarketStatus status) {
		return status==null ? null : status.code;
	}
	
	public static ClockMarketStatus getStatus(String code) {
		code = trim(lowerCase(code));
		
		for(ClockMarketStatus status : ClockMarketStatus.values()) {
			if(status.code.equals(code)) {
				return status;
			}
		}
		return null;
	}
}
