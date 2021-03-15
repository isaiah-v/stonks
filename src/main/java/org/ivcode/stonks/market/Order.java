package org.ivcode.stonks.market;

public interface Order {
	String getId();
	String getSymbol();
	OrderAction getAction();
	OrderType getType();
	OrderDuration getDuration();
	int getQuantity();
	<T> T visit(OrderVisitor<T> visitor);
}
