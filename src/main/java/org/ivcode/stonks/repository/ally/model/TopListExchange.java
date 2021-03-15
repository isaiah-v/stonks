package org.ivcode.stonks.repository.ally.model;

public enum TopListExchange {
	AMERICAN_STOCK_EXCHANGE ("A"),
	NEW_YORK_STOCK_EXCHANGE ("N"),
	NASDAQ("Q"),
	NASDAQ_BULLETIN_BOARD("U"),
	NASDAQ_OTC_OTHER("V")
	;
	private final String code;
	private TopListExchange(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	
	public static String getCode(TopListExchange exchange) {
		return exchange==null ? null : exchange.code;
	}
}
