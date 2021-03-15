package org.ivcode.stonks.market.simulator.action;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WithdrawAction extends AbstractAction {

	private final double amount;

	public WithdrawAction(double amount, Date date, String note) {
		super(date, note);
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
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
