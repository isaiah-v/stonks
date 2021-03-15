package org.ivcode.stonks.market.simulator.action;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ivcode.stonks.market.Order;

public class OrderFillAction extends AbstractOrderAction {

	/** value per stock */
	private final double value;

	
	public OrderFillAction(Order order, double value, Date date, String note) {
		super(order, date, note);
		this.value = value;
	}

	public double getValue() {
		return value;
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
