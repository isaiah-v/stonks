package org.ivcode.stonks.market.simulator.account.funds;

import static org.apache.commons.lang3.Validate.notNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class BasicFunds implements Funds {

	private final Map<LocalDate, Double> funds = new ConcurrentSkipListMap<>();
	
	public synchronized void deposit(double amount, LocalDate settleDate) {
		merge(amount, settleDate);
	}
	
	public synchronized void deposit(Funds funds) {
		deposit(funds.getFundActions());
	}
	
	public synchronized void deposit(Collection<FundAction> funds) {
		for(FundAction action : funds) {
			this.deposit(action.getAmount(), action.getSettleDate());
		}
	}
	
	public synchronized Funds withdraw(double amount) {
		List<FundAction> actions = new ArrayList<>();
		
		double remaining = amount;
		for(Map.Entry<LocalDate, Double> e : funds.entrySet()) {
			if(remaining <= 0.0) {
				break;
			}
			
			Double money = e.getValue();
			if(remaining<=money) {
				actions.add(transfer(remaining, e.getKey()));
				remaining = 0.0;
			} else {
				// remaining > money
				actions.add(transfer(money, e.getKey()));
				remaining -= money;
			}
		}
		
		BasicFunds funds = new BasicFunds();
		funds.deposit(actions);
		
		return funds;
	}
	
	private synchronized Double merge(double amount, LocalDate date) {
		return funds.merge(date, amount, BasicFunds::mergeAmounts);
	}
	
	private synchronized FundAction transfer(Double amount, LocalDate date) {
		Double value = funds.get(date);
		if(value==null || value<amount) {
			throw new IllegalArgumentException("insufficient funds for the date of " + date) ;
		}
		
		value = merge(-1 * amount, date);
		if(value!=null && value<0) {
			throw new IllegalStateException("negative value");
		}
		
		return new FundAction(amount, date);
	}
	
	@Override
	public synchronized FundSummary getSummary(LocalDate date) {
		notNull(date);
		
		double cash = 0.0;
		double unsettled = 0.0;
		LocalDate latestSettleDate = null;
		
		for(Map.Entry<LocalDate, Double> e : funds.entrySet()) {
			LocalDate settleDate = e.getKey();
			
			if(date.isAfter(settleDate) || date.isEqual(settleDate)) {
				cash += e.getValue();
			} else {
				unsettled += e.getValue();
			}
			
			if(latestSettleDate==null || latestSettleDate.isBefore(settleDate)) {
				latestSettleDate = settleDate;
			}
		}
		
		return new FundSummary(cash + unsettled, cash, unsettled, latestSettleDate, date);
	}

	@Override
	public synchronized List<FundAction> getFundActions() {
		List<FundAction> actions = new ArrayList<>();
		
		for(Map.Entry<LocalDate, Double> e : this.funds.entrySet()) {
			actions.add(new FundAction(e.getValue(), e.getKey()));
		}
		
		return actions;
	}
	
	private static Double mergeAmounts(Double d1, Double d2) {
		Double value = d1==null ? d2 : (d2==null ? d1 : d1 + d2);
		return value==null || value==0 ? null : value;
	}

	@Override
	public double getTotal() {
		double total = 0;
		
		for(Double value : funds.values()) {
			total += value;
		}
		
		return total;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
