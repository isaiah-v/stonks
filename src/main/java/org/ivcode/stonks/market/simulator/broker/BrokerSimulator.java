package org.ivcode.stonks.market.simulator.broker;

import java.util.List;

import org.ivcode.stonks.market.AccountSummary;
import org.ivcode.stonks.market.BrokerService;
import org.ivcode.stonks.market.Order;
import org.ivcode.stonks.market.simulator.account.AccountManager;
import org.ivcode.stonks.market.simulator.action.Action;
import org.ivcode.stonks.market.simulator.order.OrderManager;

public class BrokerSimulator implements BrokerService {

	private final AccountManager accountManager;
	private final OrderManager orderManager;
	private Double tradeMax;

	public BrokerSimulator(AccountManager accountManager, OrderManager orderManager) {
		this.accountManager = accountManager;
		this.orderManager = orderManager;
	}

	@Override
	public AccountSummary getAccountSummary() {
		return accountManager.getAccountSummary(tradeMax);
	}

	@Override
	public void submitOrder(Order order) {
		orderManager.submitOrder(order);
	}

	public AccountManager getAccountManager() {
		return accountManager;
	}

	public OrderManager getOrderManager() {
		return orderManager;
	}
	
	public List<Action> getActions() {
		return accountManager.getActions();
	}

	@Override
	public void setTadeMax(Double tradeMax) {
		this.tradeMax = tradeMax;
	}

	@Override
	public Double getTadeMax() {
		return tradeMax;
	}

}
