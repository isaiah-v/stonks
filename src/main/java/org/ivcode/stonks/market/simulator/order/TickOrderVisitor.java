package org.ivcode.stonks.market.simulator.order;

import org.ivcode.stonks.market.OrderMarket;
import org.ivcode.stonks.market.OrderVisitor;
import org.ivcode.stonks.market.Tick;

class TickOrderVisitor implements OrderVisitor<Double> {

	private final Tick tick;
	
	public TickOrderVisitor(Tick tick) {
		this.tick = tick;
	}

	@Override
	public Double apply(OrderMarket market) {
		return tick.getPrice();
	}
}
