package org.ivcode.stonks.market;

public enum MarketStatus {
	PRE_MARKET("PRE"),
	OPEN("REG"),
	POST_MARKET("POS"),
	CLOSED("CLO");

	private final String code;

	private MarketStatus(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
