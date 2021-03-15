
package org.ivcode.stonks.market;

import java.time.LocalDateTime;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Tick {
	private final String symbol;
	private final LocalDateTime time;
	private final double price;
	private final long volume;

	public Tick(String symbol, LocalDateTime time, double price, long volume) {
		this.symbol = symbol;
		this.time = time;
		this.price = price;
		this.volume = volume;
	}

	public String getSymbol() {
		return symbol;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public double getPrice() {
		return price;
	}

	public long getVolume() {
		return volume;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
