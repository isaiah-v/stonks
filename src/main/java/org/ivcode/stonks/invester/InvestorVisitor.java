package org.ivcode.stonks.invester;

import org.ivcode.stonks.invester.weighted.WeightedInvestor;

public interface InvestorVisitor<T, R> {
	public R apply(WeightedInvestor investor, T argument);
}
