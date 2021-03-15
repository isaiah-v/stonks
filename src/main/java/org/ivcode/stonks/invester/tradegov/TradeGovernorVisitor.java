package org.ivcode.stonks.invester.tradegov;

public interface TradeGovernorVisitor <T,R> {
	public R apply(LRSlopeTradeGovernor tradeGovernor, T argument);
	public R apply(DayTradeGovernor tradeGovernor, T argument);
}
