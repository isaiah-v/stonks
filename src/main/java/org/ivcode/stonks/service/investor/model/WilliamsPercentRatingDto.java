package org.ivcode.stonks.service.investor.model;

import java.util.concurrent.TimeUnit;

public class WilliamsPercentRatingDto {

	private String name = "Williams %R";
	private double overbought;
	private double oversold;
	private int lookback;
	private int lookbackDuration = 1;
	private TimeUnit lookbackUnit = TimeUnit.HOURS;
	private double weight;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getOverbought() {
		return overbought;
	}

	public void setOverbought(double overbought) {
		this.overbought = overbought;
	}

	public double getOversold() {
		return oversold;
	}

	public void setOversold(double oversold) {
		this.oversold = oversold;
	}

	public int getLookback() {
		return lookback;
	}

	public void setLookback(int lookback) {
		this.lookback = lookback;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
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

}
