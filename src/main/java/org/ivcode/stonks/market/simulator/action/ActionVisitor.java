package org.ivcode.stonks.market.simulator.action;

public interface ActionVisitor {
	void apply(DepositAction deposit);
	void apply(WithdrawAction withdraw);
	void apply(OrderOpenAction openOrder);
	void apply(OrderRemoveAction cancelOrder);
	void apply(OrderFillAction fillOrder);
}
