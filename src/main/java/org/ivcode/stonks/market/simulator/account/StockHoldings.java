package org.ivcode.stonks.market.simulator.account;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.ivcode.stonks.market.Stock;
import org.ivcode.stonks.market.simulator.account.funds.Funds;

public class StockHoldings {
	private final Map<String, List<Lot>> map = new HashMap<>();
	
	public void add(String symbol, int quantity, Funds funds) {
		symbol = trimToNull(upperCase(symbol));
		if(symbol==null) {
			throw new IllegalArgumentException("symbol not defined");
		}
		
		List<Lot> lots = map.computeIfAbsent(symbol, k-> new ArrayList<>());
		lots.add(new Lot(funds, quantity));
	}
	
	public void remove(String symbol, int quantity) {
		symbol = trimToNull(upperCase(symbol));
		if(symbol==null) {
			throw new IllegalArgumentException("symbol not defined");
		}
		if(quantity<0) {
			throw new IllegalArgumentException("cannot remove a negative number of stocks");
		}
		
		AccountStock stockInfo = getStock(symbol);
		if(stockInfo==null) {
			throw new IllegalArgumentException("symbol not found");
		}
		
		// make sure there is enough stock to remove
		int sellableQuantity = stockInfo.getTotalQuantity() - stockInfo.getUnsettledQuantity();
		if(sellableQuantity < quantity) {
			throw new IllegalArgumentException("insuffisant quantity");
		}
		
		List<Lot> lots = map.get(symbol);
		Iterator<Lot> it = lots.iterator();
		
		int q = quantity;
		while(q>0 && it.hasNext()) {
			Lot lot = it.next();
			int settledQuantity = lot.getQuantitySettled();
			
			if(q < settledQuantity) {
				lot.sell(q);
				q = 0;
			} else {
				lot.sell(settledQuantity);
				if(lot.getQuantity()==0) {
					it.remove();
				}
				
				q -= settledQuantity;
			}
		}
	}
	
	public Set<String> getSymbols() {
		return Collections.unmodifiableSet(map.keySet());
	}
	
	public AccountStock getStock(String symbol) {
		symbol = trimToNull(upperCase(symbol));
		if(symbol==null) {
			throw new IllegalArgumentException("symbol not defined");
		}
		
		List<Lot> lots = map.get(symbol);
		if(lots==null || lots.isEmpty()) {
			return null;
		}
		
		double totalCost = 0.0;
		int totalQuantity = 0; 
		int unsettledQuantity = 0;
		
		for(Lot lot : lots) {
			totalCost += lot.getTotalCost();
			totalQuantity += lot.getQuantity();
			unsettledQuantity += lot.getQuantityUnsettled();
		}
		
		double averagePrice = totalCost / totalQuantity;
		
		return new AccountStock(symbol, totalQuantity, unsettledQuantity, averagePrice, totalCost);
	}
	
	public List<Stock> getStocks() {
		return getSymbols().stream().map(this::getStock).filter(e->e!=null).collect(Collectors.toList());
	}
	
	private static class Lot {
		private final Funds funds;
		private final int lotQuantity;
		private final double costPerUnit;
		private int quantitySold = 0;
		
		public Lot(Funds funds, int quantity) {
			this.funds = funds;
			this.lotQuantity = quantity;
			
			this.costPerUnit = funds.getTotal() / quantity;
		}
		
		public void sell(int quantity) {
			if(getQuantity()<quantity) {
				throw new IllegalArgumentException("farts");
			}
			quantitySold += quantity;
		}

		public int getQuantity() {
			return lotQuantity - quantitySold;
		}
		
		public int getQuantitySettled() {
			int quantitySettled = (int)(funds.getTotal()/costPerUnit);
			return quantitySettled-quantitySold;
		}
		
		public int getQuantityUnsettled() {
			return getQuantity() - getQuantitySettled();
		}

		public double getTotalCost() {
			return costPerUnit * getQuantity();
		}
	}
}
