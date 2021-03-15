package org.ivcode.stonks.market;

public interface OrderVisitor<T> {
	T apply(OrderMarket market);
}
