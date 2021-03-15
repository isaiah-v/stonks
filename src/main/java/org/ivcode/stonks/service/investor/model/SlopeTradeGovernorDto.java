package org.ivcode.stonks.service.investor.model;

import java.util.concurrent.TimeUnit;

public class SlopeTradeGovernorDto {
	private String name = "LR Slope";
	private int buyTrades;
	private int sellTrades;
	private double lower;
	private double upper;
	private int lookback;
	private int lookbackDuration = 1;
	private TimeUnit lookbackUnit = TimeUnit.HOURS;
	private int waitTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBuyTrades() {
		return buyTrades;
	}

	public void setBuyTrades(int buyTrades) {
		this.buyTrades = buyTrades;
	}

	public int getSellTrades() {
		return sellTrades;
	}

	public void setSellTrades(int sellTrades) {
		this.sellTrades = sellTrades;
	}

	public double getLower() {
		return lower;
	}

	public void setLower(double lower) {
		this.lower = lower;
	}

	public double getUpper() {
		return upper;
	}

	public void setUpper(double upper) {
		this.upper = upper;
	}

	public int getLookback() {
		return lookback;
	}

	public void setLookback(int lookback) {
		this.lookback = lookback;
	}

	public int getLookbackDuration() {
		return lookbackDuration;
	}

	public void setLookbackDuration(int lookbackDuration) {
		this.lookbackDuration = lookbackDuration;
	}

	public TimeUnit getLookbackUnit() {
		return lookbackUnit;
	}

	public void setLookbackUnit(TimeUnit lookbackUnit) {
		this.lookbackUnit = lookbackUnit;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

}
