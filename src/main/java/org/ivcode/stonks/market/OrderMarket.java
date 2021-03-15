package org.ivcode.stonks.market;

import java.util.UUID;

public class OrderMarket implements Order {

	private final String id;
	private final String symbol;
	private final OrderAction action;
	private final OrderDuration duration;
	private final int quantity;

	public OrderMarket(String id, String symbol, OrderAction action, OrderDuration duration, int quantity) {
		this.id = id;
		this.symbol = symbol;
		this.action = action;
		this.duration = duration;
		this.quantity = quantity;
	}
	
	public OrderMarket(String symbol, OrderAction action, OrderDuration duration, int quantity) {
		this(UUID.randomUUID().toString(), symbol, action, duration, quantity);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getSymbol() {
		return symbol;
	}

	@Override
	public OrderAction getAction() {
		return action;
	}

	@Override
	public OrderType getType() {
		return OrderType.MARKET;
	}

	@Override
	public OrderDuration getDuration() {
		return duration;
	}

	@Override
	public int getQuantity() {
		return quantity;
	}

	@Override
	public <T> T visit(OrderVisitor<T> visitor) {
		return visitor.apply(this);
	}

}
