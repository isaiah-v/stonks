package org.ivcode.stonks.repository.ally.model;

import static org.apache.commons.lang3.StringUtils.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OptionSymbol implements Symbol {

	private static final DateTimeFormatter EXPIRATION_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");
	private static final DecimalFormat STRIKE_PRICE_FORMATTER = new DecimalFormat("00000000");

	public static enum OptionType {
		CALL("C"), PUT("P");

		private final String code;

		private OptionType(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}

		public static OptionType find(String code) {
			code = trimToNull(upperCase(code));
			for (OptionType type : OptionType.values()) {
				if (type.code.equals(code)) {
					return type;
				}
			}

			return null;
		}
	}

	private final String symbol;
	private final LocalDate expirationDate;
	private final OptionType optionType;
	private final Double strikePrice;

	public OptionSymbol(String symbol, LocalDate expirationDate, OptionType optionType, Double strikePrice) {
		this.symbol = symbol;
		this.expirationDate = expirationDate;
		this.optionType = optionType;
		this.strikePrice = strikePrice;
	}

	public String getSymbol() {
		return symbol;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public OptionType getOptionType() {
		return optionType;
	}

	public Double getStrikePrice() {
		return strikePrice;
	}

	@Override
	public String getCode() {
		return symbol + expirationDate.format(EXPIRATION_DATE_FORMATTER) + optionType.code + getStrikePriceCode();
	}
	
	private String getStrikePriceCode() {
		long value = Math.round(strikePrice * 1000.0);
		return STRIKE_PRICE_FORMATTER.format(value);
	}
}
