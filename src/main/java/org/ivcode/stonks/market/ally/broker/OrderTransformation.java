package org.ivcode.stonks.market.ally.broker;

import org.ivcode.stonks.market.Order;
import org.ivcode.stonks.market.OrderAction;
import org.ivcode.stonks.market.OrderDuration;
import org.ivcode.stonks.market.OrderMarket;
import org.ivcode.stonks.market.OrderVisitor;
import org.ivcode.stonks.repository.ally.model.fxml.Instrument;
import org.ivcode.stonks.repository.ally.model.fxml.NewOrder;
import org.ivcode.stonks.repository.ally.model.fxml.OrderQuantity;
import org.ivcode.stonks.repository.ally.model.fxml.OrderType;
import org.ivcode.stonks.repository.ally.model.fxml.SecurityType;
import org.ivcode.stonks.repository.ally.model.fxml.SideOfOrder;
import org.ivcode.stonks.repository.ally.model.fxml.TimeInForce;

public class OrderTransformation {
	public static NewOrder transformOrder(String accountId, Order order) {
		TransformerOrderVisitor visitor = new TransformerOrderVisitor(accountId);
		return order.visit(visitor);
	}

	private static NewOrder toAllyOrder(String accountId, OrderMarket order) {
		Instrument instrument = new Instrument();
		instrument.setSymbol(order.getSymbol());
		instrument.setSecurityType(SecurityType.COMMON_STOCK);

		OrderQuantity quantity = new OrderQuantity();
		quantity.setQuantity(order.getQuantity());

		NewOrder newOrder = new NewOrder();
		newOrder.setTimeInForce(toTimeInForce(order.getDuration()));
		newOrder.setSide(toSideOfOrder(order.getAction()));
		newOrder.setType(OrderType.MARKET);
		newOrder.setAccount(accountId);
		newOrder.setInstrument(instrument);
		newOrder.setOrderQuantity(quantity);

		return newOrder;
	}

	private static SideOfOrder toSideOfOrder(OrderAction action) {
		switch (action) {
		case BUY:
			return SideOfOrder.BUY;
		case SELL:
			return SideOfOrder.SELL;
		default:
			throw new IllegalArgumentException("unknown action");
		}
	}

	private static TimeInForce toTimeInForce(OrderDuration duration) {
		switch (duration) {
		case DAY:
			return TimeInForce.DAY;
		default:
			throw new IllegalArgumentException("unknown duration");
		}
	}

	private static class TransformerOrderVisitor implements OrderVisitor<NewOrder> {

		private final String accountId;

		public TransformerOrderVisitor(String accountId) {
			this.accountId = accountId;
		}

		@Override
		public NewOrder apply(OrderMarket order) {
			return toAllyOrder(accountId, order);
		}
	}
}
