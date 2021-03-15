package org.ivcode.stonks.repository.ally.model;

public class SimpleSymbol implements Symbol {

	private final String symbol;

	public SimpleSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	@Override
	public String getCode() {
		return symbol;
	}

}
