package org.ivcode.stonks.invester.weighted;

import org.ivcode.stonks.market.ChartInterval;
import org.ivcode.stonks.market.ChartRange;

public class HistoryContext {
	private final ChartRange range;
	private final ChartInterval interval;
	private final boolean includePerPost;

	public HistoryContext(ChartRange range, ChartInterval interval, boolean includePerPost) {
		this.range = range;
		this.interval = interval;
		this.includePerPost = includePerPost;
	}

	public ChartRange getRange() {
		return range;
	}

	public ChartInterval getInterval() {
		return interval;
	}

	public boolean isIncludePerPost() {
		return includePerPost;
	}

}
