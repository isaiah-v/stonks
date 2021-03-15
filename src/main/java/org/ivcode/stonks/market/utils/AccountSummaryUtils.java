package org.ivcode.stonks.market.utils;

import static org.ivcode.stonks.utils.SaftyUtils.*;

import java.util.Collection;

import org.ivcode.stonks.market.AccountSummary;
import org.ivcode.stonks.market.Stock;

public class AccountSummaryUtils {
	
	public static double getStockCost(AccountSummary summary) {
		return getStockCost(summary.getHoldings());
	}
	
	public static double getStockCost(Collection<Stock> stocks) {
		double cost = 0.0;
		for(var stock : stocks) {
			cost += stock.getCost();
		}
		
		return cost;
	}
	
	public static double getStockCost(AccountSummary summary, String symbol) {
		Stock stock = getStock(summary, symbol);
		return stock==null ? 0 : stock.getCost();
	}
	
	public static Stock getStock(AccountSummary summary, String symbol) {
		for(var stock : safe(summary.getHoldings())) {
			if(symbol.equalsIgnoreCase(stock.getSymbol())) {
				return stock;
			}
		}
		
		return null;
	}
	
	public static int getQuantity(AccountSummary summary, String symbol) {
		var stock = getStock(summary, symbol);
		return stock==null? 0 : stock.getTotalQuantity();
	}
}
