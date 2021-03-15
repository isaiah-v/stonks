package org.ivcode.stonks.market.simulator.order;

import java.time.LocalDate;

import org.ivcode.stonks.market.MarketStatus;
import org.ivcode.stonks.market.MarketTime;
import org.ivcode.stonks.market.Order;
import org.ivcode.stonks.utils.TimeUtils;

public class OrderExpirationUtils {
	
	public static boolean isExpired(OpenOrder openOrder, MarketTime time) {

		LocalDate orderDate = TimeUtils.toLocalDate(openOrder.getOpenDate());
		LocalDate today = TimeUtils.toLocalDate(time.getTime());
		
		Order order = openOrder.getOrder();
		
		switch(order.getDuration()) {
		case DAY:
			return isExpiredDay(orderDate, today, time.getMarketStatus());
		default:
			throw new IllegalArgumentException("unknown order duration: " + order.getDuration());
		}
	}
	
	private static boolean isExpiredDay(LocalDate orderDate, LocalDate today, MarketStatus currentMarketStatus) {
		if(today.isAfter(orderDate)) {
			// if more than a day has passed, then it's expired
			return true;
		} else if(today.isEqual(orderDate)) {
			// if it's the same day, check if the order has expried within the day
			return MarketStatus.OPEN.compareTo(currentMarketStatus) < 0;
		} else {
			// order date is in the future
			throw new IllegalStateException("order date is in the future");
		} 
	}
}
