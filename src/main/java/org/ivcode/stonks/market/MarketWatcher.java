package org.ivcode.stonks.market;

public interface MarketWatcher {
	void onTick(String symbol, Tick tick);
}
