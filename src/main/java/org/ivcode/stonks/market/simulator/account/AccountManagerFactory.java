package org.ivcode.stonks.market.simulator.account;

import org.ivcode.stonks.market.MarketTime;
import org.ivcode.stonks.market.simulator.businessday.SimpleBusonessDays;
import org.ivcode.stonks.market.simulator.order.OrderManager;
import org.ivcode.stonks.market.simulator.properties.SimulatorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountManagerFactory {
	
	@Autowired
	private MarketTime marketTime;
	
	@Autowired
	private SimulatorProperties properties;
	
	/**
	 * Creates an account manager using the live {@link MarketTime}
	 * @return
	 * 		Account manager using the live {@link MarketTime}
	 */
	public AccountManager createAccountManager(OrderManager orderManager) {
		return new AccountManager(new SimpleBusonessDays(), marketTime, orderManager, properties);
	}
	
	/**
	 * Creates an account manager using the given {@link MarketTime}
	 * @param marketTime
	 * 		The market time to be used by the account manager
	 * @return
	 * 		Account manager using the given {@link MarketTime}
	 */
	public AccountManager createAccountManager(OrderManager orderManager, MarketTime marketTime) {
		return new AccountManager(new SimpleBusonessDays(), marketTime, orderManager, properties);
	}
}
