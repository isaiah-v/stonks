package org.ivcode.stonks.market.ally.broker;

import static org.ivcode.stonks.utils.SaftyUtils.*;

import java.util.ArrayList;
import java.util.List;

import org.ivcode.stonks.market.AccountSummary;
import org.ivcode.stonks.market.Stock;
import org.ivcode.stonks.market.common.BasicAccountSummary;
import org.ivcode.stonks.repository.ally.model.AccountBalance;
import org.ivcode.stonks.repository.ally.model.AccountHolding;
import org.ivcode.stonks.repository.ally.model.AccountHoldingInstrument;
import org.ivcode.stonks.repository.ally.model.AccountHoldings;
import org.ivcode.stonks.repository.ally.model.AccountMoney;
import org.ivcode.stonks.repository.ally.model.AccountResponse;

class AccountSummaryTransformer {
	public static AccountSummary toAccountSummary(AccountResponse response, Double tradeMax) {
		
		AccountBalance balance = response.getAccountBalance();
		AccountMoney money = balance.getMoney();
		
		double cash = safe(money.getTotal() - money.getUnsettledFunds());
		double unsettled = safe(money.getUnsettledFunds());
		
		double tradableFunds = cash;
		if(tradeMax!=null) {
			double holding = getHoldingCost(response);
			tradableFunds = Math.min(tradeMax - holding, cash);
		}
		
		BasicAccountSummary summary = new BasicAccountSummary();
		summary.setId(balance.getAccount());
		summary.setAvailableCash(cash);
		summary.setTradableFunds(tradableFunds);
		summary.setUnsettledFunds(unsettled);
		summary.setHoldings(toStock(response.getAccountHoldings()));
		
		return summary;
	}
	
	private static List<Stock> toStock(AccountHoldings accountHoldings) {
		List<Stock> stocks = new ArrayList<>();
		
		List<AccountHolding> holdingList = accountHoldings.getHoldings();
		for(AccountHolding holding : safe(holdingList)) {
			AccountHoldingInstrument instrument = holding.getInstrument();
			String symbol = instrument.getSymbol();
			int quantity = holding.getQuantity();
			double averagePrice = holding.getPurchasePrice();
			double cost = holding.getCostBasis();
			
			stocks.add(new Stock(symbol, quantity, averagePrice, cost));
		}
		
		return stocks;
	}
	
	private static double getHoldingCost(AccountResponse response) {
		var accountHoldings = response.getAccountHoldings();
		if(accountHoldings==null) {
			return 0;
		}
		
		var total = 0.0;
		for(var holding : safe(accountHoldings.getHoldings())) {
			total += holding.getCostBasis();
		}
		
		return total;
	}
}
