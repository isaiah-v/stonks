package org.ivcode.stonks.market.simulator.order;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ivcode.stonks.market.Order;

public class OpenOrders implements Iterable<OpenOrder> {
	private final Map<String, OpenOrder> openOrders = new HashMap<>();
	
	public boolean add(OpenOrder openOrder) {
		Order order = openOrder.getOrder();
		String id = order.getId();
		
		return openOrders.putIfAbsent(id, openOrder)==null;
	}
	
	public Collection<OpenOrder> getOpenOrders() {
		return Collections.unmodifiableCollection(openOrders.values());
	}
	
	public OpenOrder remove(OpenOrder openOrder) {
		if(openOrder==null) {
			throw new IllegalArgumentException("open order not defined");
		}
		return remove(openOrder.getOrder());
	}
	
	public OpenOrder remove(Order order) {
		if(order==null) {
			throw new IllegalArgumentException("order is not defined");
		}
		return remove(order.getId());
	}
	
	public OpenOrder remove(String id) {
		if(isBlank(id)) {
			throw new IllegalArgumentException("order id is not defined");
		}
		return openOrders.remove(id);
	}

	@Override
	public Iterator<OpenOrder> iterator() {
		return getOpenOrders().iterator();
	}
	
	public boolean isEmpty() {
		return openOrders.isEmpty();
	}
}
