package org.ivcode.stonks.market.simulator.action;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ivcode.stonks.market.Order;

public class OrderOpenAction extends AbstractOrderAction {

	/** cost per stock */
	private final double cost;

	public OrderOpenAction(Order order, double cost, Date date, String note) {
		super(order, date, note);
		this.cost = cost;
	}

	public double getCost() {
		return cost;
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
