package org.ivcode.stonks.market;

import java.util.Date;

public interface MarketTime {
	/**
	 * Returns the current time.
	 * @return
	 */
	Date getTime();
	
	/**
	 * Returns the current status
	 * @return
	 */
	MarketStatus getMarketStatus();
	
	boolean addMarketStatusListener(MarketStatusListener listener);
	boolean removeMarketStatusListener(MarketStatusListener listener);
}
