package org.ivcode.stonks.repository.yahoo.v8.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MarketTimeZone {
	private String dst;
	private String gmtoffset;

	@JsonProperty("short")
	private String zone;

	@JsonProperty("$text")
	private String text;

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public String getGmtoffset() {
		return gmtoffset;
	}

	public void setGmtoffset(String gmtoffset) {
		this.gmtoffset = gmtoffset;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
