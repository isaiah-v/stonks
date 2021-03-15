package org.ivcode.stonks.market.simulator.account;

import java.time.LocalDate;
import java.util.List;

import org.ivcode.stonks.market.AccountSummary;
import org.ivcode.stonks.market.Order;
import org.ivcode.stonks.market.Stock;
import org.ivcode.stonks.market.common.BasicAccountSummary;
import org.ivcode.stonks.market.simulator.account.funds.BasicFunds;
import org.ivcode.stonks.market.simulator.account.funds.FundSummary;
import org.ivcode.stonks.market.simulator.account.funds.Funds;
import org.ivcode.stonks.market.simulator.action.ActionVisitor;
import org.ivcode.stonks.market.simulator.action.DepositAction;
import org.ivcode.stonks.market.simulator.action.OrderFillAction;
import org.ivcode.stonks.market.simulator.action.OrderOpenAction;
import org.ivcode.stonks.market.simulator.action.OrderRemoveAction;
import org.ivcode.stonks.market.simulator.action.WithdrawAction;
import org.ivcode.stonks.market.simulator.businessday.BusinessDays;
import org.ivcode.stonks.market.simulator.properties.SimulatorProperties;
import org.ivcode.stonks.market.utils.AccountSummaryUtils;

public class AccountActionVisitor implements ActionVisitor {

	private final SimulatorProperties properties;
	
	private final BusinessDays businessDays;

	private final BasicFunds funds = new BasicFunds();
	private final OpenOrders openOrders = new OpenOrders();
	private final StockHoldings stockHoldings = new StockHoldings();

	public AccountActionVisitor(SimulatorProperties properties, BusinessDays businessDays) {
		this.properties = properties;
		this.businessDays = businessDays;
	}

	@Override
	public void apply(DepositAction deposit) {
		double amount = deposit.getAmount();
		if (amount < 0) {
			throw new IllegalArgumentException();
		}

		funds.deposit(amount, deposit.getDate().toLocalDate());
	}

	@Override
	public void apply(WithdrawAction withdraw) {
		double amount = withdraw.getAmount();
		if (amount < 0) {
			throw new IllegalArgumentException();
		}

		funds.withdraw(amount);
	}

	@Override
	public void apply(OrderOpenAction openOrder) {
		Order order = openOrder.getOrder();

		switch (order.getAction()) {
		case BUY:
			openBuyOrder(openOrder);
			break;
		case SELL:
			openSellOrder(openOrder);
			break;
		default:
			throw new IllegalStateException("unknown order action: " + order.getAction());
		}
	}
	
	private void openBuyOrder(OrderOpenAction openOrder) {
		Order order = openOrder.getOrder();
		double cost = openOrder.getCost();
		double totalCost = cost * order.getQuantity();

		double buyingPower = getBuyingPower();

		if (totalCost > buyingPower) {
			throw new IllegalArgumentException("cannot place order due to insufesent funds");
		}

		openOrders.addOpenOrder(order, cost);
	}
	
	private void openSellOrder(OrderOpenAction openOrder) {
		Order order = openOrder.getOrder();
		openOrders.addOpenOrder(order, 0);
	}

	@Override
	public void apply(OrderRemoveAction removeOrder) {
		Order order = removeOrder.getOrder();
		openOrders.removeOpenOrder(order);
	}

	@Override
	public void apply(OrderFillAction fillOrder) {
		Order order = fillOrder.getOrder();

		switch (order.getAction()) {
		case BUY:
			fillBuyOrder(fillOrder);
			break;
		case SELL:
			fillSellOrder(fillOrder);
			break;
		default:
			throw new IllegalStateException("unknown order action: " + order.getAction());
		}
	}

	public AccountSummary createAccountSummary(String id, LocalDate date, Double tradeMax) {
		FundSummary fundSummary = funds.getSummary(date);
		
		double cash = fundSummary.getCash();
		double unsettledFunds = fundSummary.getUnsettled();
		List<Stock> holdings = stockHoldings.getStocks();
		
		double tradeableFuns = cash;
		if(tradeMax!=null) {
			double stockCost = AccountSummaryUtils.getStockCost(holdings);
			tradeableFuns = Math.min(tradeMax-stockCost, cash);
		}

		return new BasicAccountSummary(id, cash, tradeableFuns, unsettledFunds, holdings);
	}

	private double getBuyingPower() {
		return funds.getTotal() - openOrders.getTotalCost();
	}

	private void fillSellOrder(OrderFillAction fillOrder) {
		// Warning: if an exception is thrown, the state of the account may get corrupt.
		// In the future, I may want to implement something like transactions

		Order order = fillOrder.getOrder();
		openOrders.removeOpenOrder(order);

		String symbol = order.getSymbol();
		int quantity = order.getQuantity();
		double price = fillOrder.getValue();

		stockHoldings.remove(symbol, quantity);
		
		int daysToSettle = properties.getDaysToSettle();
		LocalDate settleDate = businessDays.getBusinessDate(fillOrder.getDate().toLocalDate(), daysToSettle);
		
		funds.deposit(quantity * price, settleDate);
	}

	private void fillBuyOrder(OrderFillAction fillOrder) {
		// Warning: if an exception is thrown, the state of the account may get corrupt.
		// In the future, I may want to implement something like transactions

		Order order = fillOrder.getOrder();

		String symbol = order.getSymbol();
		int quantity = order.getQuantity();
		double price = fillOrder.getValue();

		double amount = quantity * price;

		double buyingPower = getBuyingPower();
		Double orderCost = openOrders.getOrderCost(order);
		double buyingPowerWithOrderCost = buyingPower + (orderCost == null ? 0 : orderCost);

		if (buyingPowerWithOrderCost < amount) {
			throw new IllegalArgumentException();
		}
		openOrders.removeOpenOrder(order);

		Funds funds = this.funds.withdraw(amount);

		stockHoldings.add(symbol, quantity, funds);
	}
}
