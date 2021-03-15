package org.ivcode.stonks.market.simulator.order;

import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.commons.lang3.StringUtils.upperCase;
import static org.ivcode.stonks.market.simulator.order.OrderExpirationUtils.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.ivcode.stonks.market.MarketStatus;
import org.ivcode.stonks.market.MarketTime;
import org.ivcode.stonks.market.MarketWatcherService;
import org.ivcode.stonks.market.Order;
import org.ivcode.stonks.market.QuoteService;
import org.ivcode.stonks.market.Tick;

public class OrderManager {

	private final QuoteService quoteable;
	private final MarketTime marketTime;
	
	private final Set<OrderListener> globalListeners = new LinkedHashSet<>();
	private final Map<String, OpenOrders> openOrdersMap = new ConcurrentHashMap<>();

	public OrderManager(MarketWatcherService market, QuoteService quoteable, MarketTime marketTime) {
		this.quoteable = quoteable;
		this.marketTime = marketTime;
		
		
		market.superSubscribe(this::onTick);
		marketTime.addMarketStatusListener(this::onMarketStatusChange);
	}
	
	public void submitOrder(Order order, OrderListener listener) {
		OpenOrder openOrder = new OpenOrder(order, marketTime.getTime(), listener);
		
		if(isExpired(openOrder, marketTime)) {
			// TODO

		}
		
		QuoteOrderVisitor visitor = new QuoteOrderVisitor(quoteable);
		Double value = order.visit(visitor);
		if(value==null) {
			// cannot fill, open order
			String sym = order.getSymbol();
			OpenOrders openOrders = openOrdersMap.computeIfAbsent(sym, k-> new OpenOrders());
			
			openOrders.add(openOrder);
			triggerOpen(openOrder, order.visit(new CostOrderVisitor()));
		} else {
			// fill now
			triggerFill(openOrder, value);
		}
	}

	public void submitOrder(Order order) {
		submitOrder(order, null);
	}

	public boolean removeOrder(Order order) {
		OpenOrder value = removeOpenOrder(order);
		triggerRemove(value);
		
		return value!=null;
	}

	public boolean addListener(OrderListener listener) {
		return globalListeners.add(listener);
	}

	public boolean removeListener(OrderListener listener) {
		return globalListeners.add(listener);
	}
	
	private void onTick(String symbol, Tick tick) {
		symbol = trim(upperCase(symbol));
		OpenOrders orders = openOrdersMap.get(symbol);
		
		if(orders==null || orders.isEmpty()) {
			return;
		}
		
		for(OpenOrder openOrder : orders) {
			onTick(openOrder, tick);
		}
	}
	
	private void onTick(OpenOrder openOrder, Tick tick) {
		Order order = openOrder.getOrder();
		
		TickOrderVisitor visitor = new TickOrderVisitor(tick);
		
		Double value = order.visit(visitor);
		if(value==null) {
			// null indicates the order cannot be filled
			return;
		}
		
		removeOpenOrder(openOrder);
		triggerFill(openOrder, value);
	}
	
	private void onMarketStatusChange(MarketStatus marketStatus) {
		List<OpenOrder> expired = new ArrayList<>();
		
		// find expired orders
		for(OpenOrders openOrders : openOrdersMap.values()) {
			for(OpenOrder openOrder : openOrders) {
				if(OrderExpirationUtils.isExpired(openOrder, marketTime)) {
					expired.add(openOrder);
				}
			}
		}
		
		// remove all expired orders
		for(OpenOrder openOrder : expired) {
			removeOpenOrder(openOrder);
		}
		
		// trigger events
		for(OpenOrder openOrder : expired) {
			triggerRemove(openOrder);
		}
	}
	
	private void triggerOpen(OpenOrder openOrder, double cost) {
		// trigger global listeners (mostly system listeners)
		for(OrderListener listener : globalListeners) {
			try {
				listener.onOpen(openOrder.getOrder(), cost);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		// trigger attached listeners
		try {
			OrderListener listener = openOrder.getListener();
			if(listener!=null) {
				listener.onOpen(openOrder.getOrder(), cost);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	private void triggerRemove(OpenOrder openOrder) {
		// trigger global listeners (mostly system listeners)
		for(OrderListener listener : globalListeners) {
			try {
				listener.onRemove(openOrder.getOrder());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		// trigger attached listeners
		try {
			OrderListener listener = openOrder.getListener();
			if(listener!=null) {
				listener.onRemove(openOrder.getOrder());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void triggerFill(OpenOrder openOrder, double value) {
		// trigger global listeners (mostly system listeners)
		for(OrderListener listener : globalListeners) {
			try {
				listener.onFill(openOrder.getOrder(), value);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		// trigger attached listeners
		try {
			OrderListener listener = openOrder.getListener();
			if(listener!=null) {
				listener.onFill(openOrder.getOrder(), value);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private OpenOrder removeOpenOrder(OpenOrder openOrder) {
		return removeOpenOrder(openOrder.getOrder());
	}
	
	private OpenOrder removeOpenOrder(Order order) {
		String sym = order.getSymbol();
		
		OpenOrders openOrders = openOrdersMap.get(sym);
		OpenOrder value = openOrders.remove(order);
				
		if(value!=null && openOrders.isEmpty()) {
			openOrdersMap.remove(sym);
		}
		
		return value;
	}
}
