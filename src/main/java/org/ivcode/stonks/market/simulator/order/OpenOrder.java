package org.ivcode.stonks.market.simulator.order;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.Date;

import org.ivcode.stonks.market.Order;

public class OpenOrder {
	private final Order order;
	private final Date openDate;
	private final OrderListener listener;

	public OpenOrder(Order order, Date openDate, OrderListener listener) {
		if (order == null || isBlank(order.getId())) {
			throw new IllegalArgumentException("order must be defined with an ID");
		}
		if (openDate == null) {
			throw new IllegalArgumentException("order date must be defined");
		}

		this.order = order;
		this.openDate = openDate;
		this.listener = listener;
	}

	public Order getOrder() {
		return order;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public OrderListener getListener() {
		return listener;
	}

}
