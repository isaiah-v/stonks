package org.ivcode.stonks.invester.tradegov;

public class LRSlopeTradeContext {
	private int waitTime;
	private int minumumTrades;
	private int maximumTrades;

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	public int getMinumumTrades() {
		return minumumTrades;
	}

	public void setMinumumTrades(int minumumTrades) {
		this.minumumTrades = minumumTrades;
	}

	public int getMaximumTrades() {
		return maximumTrades;
	}

	public void setMaximumTrades(int maximumTrades) {
		this.maximumTrades = maximumTrades;
	}

}
