package org.ivcode.stonks.market.simulator.action;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ivcode.stonks.market.Order;

abstract class AbstractOrderAction extends AbstractAction {

	private final Order order;

	public AbstractOrderAction(Order order, Date date, String note) {
		super(date, note);
		this.order = order;
	}

	public Order getOrder() {
		return order;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
