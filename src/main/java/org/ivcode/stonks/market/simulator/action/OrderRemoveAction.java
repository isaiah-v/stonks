package org.ivcode.stonks.market.simulator.action;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ivcode.stonks.market.Order;

public class OrderRemoveAction extends AbstractOrderAction {

	public OrderRemoveAction(Order order, Date date, String note) {
		super(order, date, note);
	}

	@Override
	public void visit(ActionVisitor visitor) {
		visitor.apply(this);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
