package org.ivcode.stonks.market;

import java.util.List;

/**
 * Account information
 * @author isaiah
 *
 */
public interface AccountSummary {
	String getId();
	
	/**
	 * Available cash
	 * @return
	 */
	double getAvailableCash();
	
	double getTradableFunds();
	
	/**
	 * Funds that are available but not yet settled.
	 * 
	 * @return
	 */
	double getUnsettledFunds();
	List<Stock> getHoldings();
}
