package org.ivcode.stonks.repository.yahoo.v8.model;

import java.util.List;

public class ChartTradingPeriods {
	private List<List<ChartTradingPeriod>> pre;
	private List<List<ChartTradingPeriod>> regular;
	private List<List<ChartTradingPeriod>> post;

	public List<List<ChartTradingPeriod>> getPre() {
		return pre;
	}

	public void setPre(List<List<ChartTradingPeriod>> pre) {
		this.pre = pre;
	}

	public List<List<ChartTradingPeriod>> getRegular() {
		return regular;
	}

	public void setRegular(List<List<ChartTradingPeriod>> regular) {
		this.regular = regular;
	}

	public List<List<ChartTradingPeriod>> getPost() {
		return post;
	}

	public void setPost(List<List<ChartTradingPeriod>> post) {
		this.post = post;
	}

}
