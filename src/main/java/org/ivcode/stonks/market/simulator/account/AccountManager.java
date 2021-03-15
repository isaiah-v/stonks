package org.ivcode.stonks.market.simulator.account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ivcode.stonks.market.AccountSummary;
import org.ivcode.stonks.market.MarketTime;
import org.ivcode.stonks.market.Order;
import org.ivcode.stonks.market.simulator.action.Action;
import org.ivcode.stonks.market.simulator.action.DepositAction;
import org.ivcode.stonks.market.simulator.action.OrderFillAction;
import org.ivcode.stonks.market.simulator.action.OrderOpenAction;
import org.ivcode.stonks.market.simulator.action.OrderRemoveAction;
import org.ivcode.stonks.market.simulator.action.WithdrawAction;
import org.ivcode.stonks.market.simulator.businessday.BusinessDays;
import org.ivcode.stonks.market.simulator.order.OrderListener;
import org.ivcode.stonks.market.simulator.order.OrderManager;
import org.ivcode.stonks.market.simulator.properties.SimulatorProperties;
import org.ivcode.stonks.utils.TimeUtils;

public class AccountManager implements OrderListener {

	private final String id = "ID";
	private final MarketTime marketTime;
	
	private final AccountActionVisitor accountActionVisitor;

	private final List<Action> actions = new ArrayList<>();

	public AccountManager(
			BusinessDays businessDays,
			MarketTime marketTime,
			OrderManager orderManager,
			SimulatorProperties properties) {
		
		this.marketTime = marketTime;
		this.accountActionVisitor = new AccountActionVisitor(properties, businessDays);
		
		orderManager.addListener(this);
	}

	public AccountSummary getAccountSummary(Double tradeMax) {
		return accountActionVisitor.createAccountSummary(id, TimeUtils.toLocalDate(marketTime.getTime()), tradeMax);
	}
	
	public List<Action> getActions() {
		return Collections.unmodifiableList(actions);
	}

	public void deposite(double amount) {
		addAction(new DepositAction(amount, marketTime.getTime(), null));
	}

	public void withdraw(double amount) {
		addAction(new WithdrawAction(amount, marketTime.getTime(), null));
	}

	@Override
	public void onOpen(Order order, double cost) {
		addAction(new OrderOpenAction(order, cost, marketTime.getTime(), null));
	}

	@Override
	public void onRemove(Order order) {
		addAction(new OrderRemoveAction(order, marketTime.getTime(), null));
	}

	@Override
	public void onFill(Order order, double value) {
		addAction(new OrderFillAction(order, value, marketTime.getTime(), null));
	}

	private void addAction(Action action) {
		action.visit(this.accountActionVisitor);
		actions.add(action);
	}
}