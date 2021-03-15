package org.ivcode.stonks.market.simulator.order;

import org.ivcode.stonks.market.Order;

public interface OrderListener {
	void onOpen(Order order, double cost);
	void onRemove(Order order);
	void onFill(Order order, double value);
}
