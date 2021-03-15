package org.ivcode.stonks.market.simulator.broker;

import org.ivcode.stonks.market.simulator.account.AccountManager;
import org.ivcode.stonks.market.simulator.account.AccountManagerFactory;
import org.ivcode.stonks.market.simulator.order.OrderManager;
import org.ivcode.stonks.market.simulator.order.OrderManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrokerSimulatorFactory {
	
	@Autowired
	private OrderManagerFactory orderManagerFactory;
	
	@Autowired
	private AccountManagerFactory accountManagerFactory;
	
	public BrokerSimulator createBrokerService() {
		OrderManager orderManager = orderManagerFactory.createOrderManager();
		AccountManager accountManager = accountManagerFactory.createAccountManager(orderManager);
		
		return new BrokerSimulator(accountManager, orderManager);
	}
}
