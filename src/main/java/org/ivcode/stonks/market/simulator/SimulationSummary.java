package org.ivcode.stonks.market.simulator;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ivcode.stonks.market.simulator.action.Action;

public class SimulationSummary {
	private final double startMarketValue;
	private final double marketValue;
	private final double cash;
	private final double unsettled;
	private final double change;
	private final List<SimulationStock> holdings;
	private final List<Action> actions;

	public SimulationSummary(double startMarketValue, double marketValue, double cash, double unsettled, double change,
			List<SimulationStock> holdings, List<Action> actions) {
		this.startMarketValue = startMarketValue;
		this.marketValue = marketValue;
		this.cash = cash;
		this.unsettled = unsettled;
		this.change = change;
		this.holdings = holdings;
		this.actions = actions;
	}

	public double getStartMarketValue() {
		return startMarketValue;
	}

	public double getMarketValue() {
		return marketValue;
	}

	public double getCash() {
		return cash;
	}

	public double getUnsettled() {
		return unsettled;
	}

	public double getChange() {
		return change;
	}

	public List<SimulationStock> getHoldings() {
		return holdings;
	}

	public List<Action> getActions() {
		return actions;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
