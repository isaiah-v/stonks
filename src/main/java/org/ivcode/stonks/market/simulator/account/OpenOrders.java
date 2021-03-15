package org.ivcode.stonks.market.simulator.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.ivcode.stonks.market.Order;

public class OpenOrders {

	private final Map<String, OpenOrder> openOrderMap = new HashMap<>();

	public void addOpenOrder(Order order, double cost) {
		String id = getOrderId(order);
		if (id == null) {
			throw new IllegalArgumentException("order does not contain an id");
		}

		OpenOrder openOrder = openOrderMap.putIfAbsent(id, new OpenOrder(order, cost));
		if (openOrder != null) {
			throw new IllegalArgumentException("order already exists: " + order.getId());
		}
	}

	public boolean removeOpenOrder(Order order) {
		String id = getOrderId(order);
		if (id == null) {
			return false;
		}

		return openOrderMap.remove(id) != null;
	}

	public double getTotalCost() {
		double total = 0.0;
		for (OpenOrder openOrder : openOrderMap.values()) {
			double costPerStock = openOrder.getCost();
			double quantity = openOrder.getOrder().getQuantity();

			total += (costPerStock * quantity);
		}
		return total;
	}

	/**
	 * Returns the total cost for the given order. If {@code null} is returned, the
	 * order was not found
	 * 
	 * @param order The order
	 * @return The order's total cost
	 */
	public Double getOrderCost(Order order) {
		String id = getOrderId(order);
		if (id == null) {
			return null;
		}

		OpenOrder openOrder = openOrderMap.get(getOrderId(order));
		if (openOrder == null) {
			return null;
		}

		return openOrder.getCost() * openOrder.getOrder().getQuantity();
	}

	public List<Order> getOrders() {
		return openOrderMap.values().stream().map(OpenOrder::getOrder).collect(Collectors.toList());
	}

	private String getOrderId(Order order) {
		if (order == null) {
			return null;
		}

		String id = order.getId();
		if (id == null || id.isBlank()) {
			return null;
		}

		return id;
	}

	private static final class OpenOrder {
		private final Order order;
		private final double cost;

		public OpenOrder(Order order, double cost) {
			if (order == null) {
				throw new IllegalArgumentException("null value");
			}
			this.order = order;
			this.cost = cost;
		}

		public Order getOrder() {
			return order;
		}

		public double getCost() {
			return cost;
		}

	}
}
