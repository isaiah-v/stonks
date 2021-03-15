package org.ivcode.stonks.repository.yahoo.v8.model;

public class ChartCurrentTradingPeriods {
	
	private ChartTradingPeriod pre;
	private ChartTradingPeriod regular;
	private ChartTradingPeriod post;

	public ChartTradingPeriod getPre() {
		return pre;
	}

	public void setPre(ChartTradingPeriod pre) {
		this.pre = pre;
	}

	public ChartTradingPeriod getRegular() {
		return regular;
	}

	public void setRegular(ChartTradingPeriod regular) {
		this.regular = regular;
	}

	public ChartTradingPeriod getPost() {
		return post;
	}

	public void setPost(ChartTradingPeriod post) {
		this.post = post;
	}

}
